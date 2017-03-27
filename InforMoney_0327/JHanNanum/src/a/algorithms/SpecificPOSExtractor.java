/*  Copyright 2010, 2011 Semantic Web Research Center, KAIST

This file is part of JHanNanum.

JHanNanum is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JHanNanum is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JHanNanum.  If not, see <http://www.gnu.org/licenses/>   */

package a.algorithms;

import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PosProcessor.PosProcessor;

/**
 * 이 클래스는 특정 태그로 태깅된 형태소를 추출합니다.
 * 추출 하려는 형태소 : 보통명사  NC, 고유명사  NQ, 대명사  NP, 동사 PV, 형용사 PA, 보조용언 PX, 부사 MA
 * 
 */
public class SpecificPOSExtractor implements PosProcessor {
	
	CorrectionDic corDic = null;
	
	/** the buffer for noun morphemes */
	private LinkedList<String> Morphemes = null;
	
	/** the buffer for tags of the morphemes */
	private LinkedList<String> Tags = null;

	@Override
	public void initialize(String baseDir, String configFile) throws Exception {
		corDic = new CorrectionDic();
		corDic.readDic(".\\data\\kE\\dic_correction.txt");
		Morphemes = new LinkedList<String>();
		Tags = new LinkedList<String>();
	}
	

	@Override
	public void shutdown() {
		
	}

	@Override
	public Sentence doProcess(Sentence st) {
		String[] tags;
		String[] morphemes;
		
		Eojeol[] eojeolSet = st.getEojeols();
				
		for (int i = 0; i < eojeolSet.length; i++) {
			morphemes = eojeolSet[i].getMorphemes();
			tags = eojeolSet[i].getTags();
			Morphemes.clear();
			Tags.clear();
			//보통명사  NC, 고유명사  NQ, 대명사  NP, 동사 PV, 형용사 PA, 보조용언 PX, 부사 MA
			for (int j = 0; j < tags.length; j++) {
				//char c = tags[j].charAt(0);
				String c = tags[j];
				//각 형태소를 보정된 단어 사전에서 찾아 보정시킵니다.
				String corMorph = corDic.get(morphemes[j]);
				if (c.equals("NC")) {
					Morphemes.add(morphemes[j]);
					Tags.add(tags[j]);
				}else if (c.equals("NQ")) {
					Morphemes.add(morphemes[j]);
					Tags.add(tags[j]);
				}else if (c.equals("NP")) {
					Morphemes.add(morphemes[j]);
					Tags.add(tags[j]);
				}else if (c.charAt(0)=='P') {
					Morphemes.add(corMorph);
					Tags.add(tags[j]);
				}else if (c.equals("MA")) {
					Morphemes.add(morphemes[j]);
					Tags.add(tags[j]);
				}else if (c.equals("SF")) {
					Morphemes.add(null);
						Tags.add(tags[j]);
				}/*else if (c.equals("F")) {
					Morphemes.add(morphemes[j]);
					Tags.add("NC");
				}*/
			}
			eojeolSet[i].setMorphemes(Morphemes.toArray(new String[0]));
			eojeolSet[i].setTags(Tags.toArray(new String[0]));
		}
		
		st.setEojeols(eojeolSet);
		
		return st;
	}
}
