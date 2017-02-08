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

package a_test.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PosProcessor.PosProcessor;
import kr.ac.kaist.swrc.jhannanum.share.TagMapper;

/**
 * 이 플러그인은  66태그를 22태그로 변경 시켜주며 특정 형태소를 추출한다.
 * 추출 하려는 형태소 : 보통명사  NC, 고유명사  NQ, 대명사  NP, 동사 PV, 형용사 PA, 보조용언 PX, 부사 MA
 * 
 */
public class SpecificTagExtractor implements PosProcessor {
	/** the level of analysis */
	final private int TAG_LEVEL = 2;
	
	/** the buffer for noun morphemes */
	private LinkedList<String> SPMorphemes = null;
	
	/** the buffer for tags of the morphemes */
	private LinkedList<String> SPTags = null;

	
	@Override
	public void initialize(String baseDir, String configFile) throws Exception {
		SPMorphemes = new LinkedList<String>();
		SPTags = new LinkedList<String>();
	}

	@Override
	public void shutdown() {
		
	}

	/**
	 * It extracts the morphemes which were recognized as noun after POS tagging.
	 * @param st - the POS tagged sentence
	 * @return the sentence in which only nouns were remained
	 */
	@Override
	public Sentence doProcess(Sentence st) {
		String prevTag = null;
		boolean changed = false;
		String[] tags;
		String[] morphemes;
		
		Eojeol[] eojeolSet = st.getEojeols();
				
		for (int i = 0; i < eojeolSet.length; i++) {
			morphemes = eojeolSet[i].getMorphemes();
			tags = eojeolSet[i].getTags();
			SPMorphemes.clear();
			SPTags.clear();
			//보통명사  NC, 고유명사  NQ, 대명사  NP, 동사 PV, 형용사 PA, 보조용언 PX, 부사 MA
			for (int j = 0; j < tags.length; j++) {
				//char c = tags[j].charAt(0);
				String c = tags[j];
				if (c.equals("NC")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}else if (c.equals("NQ")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}else if (c.equals("NP")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}else if (c.charAt(0)=='P') {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}else if (c.equals("MA")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}/*else if (c.equals("F")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add("NC");
				}*/
			}
			eojeolSet[i].setMorphemes(SPMorphemes.toArray(new String[0]));
			eojeolSet[i].setTags(SPTags.toArray(new String[0]));
		}
		
		st.setEojeols(eojeolSet);
		
		return st;
	}
}
