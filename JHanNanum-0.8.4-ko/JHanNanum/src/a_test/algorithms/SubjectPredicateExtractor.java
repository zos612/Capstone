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
 * This plug-in extracts the morphemes recognized as a noun after Part Of Speech tagging was done.
 * 
 * It is a POS Processor plug-in which is a supplement plug-in of phase 3 in HanNanum work flow.
 * 
 * @author Sangwon Park (hudoni@world.kaist.ac.kr), CILab, SWRC, KAIST
 */
public class SubjectPredicateExtractor implements PosProcessor {
	/** the level of analysis */
	final private int TAG_LEVEL = 2;
	
	/** the buffer for noun morphemes */
	private LinkedList<String> SPMorphemes = null;
	
	/** the buffer for tags of the morphemes */
	private LinkedList<String> SPTags = null;
	
	/** temporary list for new tags */
	private ArrayList<String> tagList = null;
	
	/** temporary list for morpheme tags */
	private ArrayList<String> morphemeList = null;
	

	@Override
	public void initialize(String baseDir, String configFile) throws Exception {
		tagList = new ArrayList<String>();
		morphemeList = new ArrayList<String>();
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
		
		//어절단위 테그셋 가져오기
		for (int i = 0; i < eojeolSet.length; i++) {
			tags = eojeolSet[i].getTags();
			prevTag = "";
			changed = false;
			
			//69pos를 22pos로 바꾼다.
			for (int j = 0; j < tags.length; j++) {
				tags[j] = TagMapper.getKaistTagOnLevel(tags[j], TAG_LEVEL);
				
				if (tags[j].equals(prevTag)) {
					changed = true;
				}
				prevTag = tags[j];
			}
			
			if (changed) {
				tagList.clear();
				morphemeList.clear();
				morphemes = eojeolSet[i].getMorphemes();
				
				//어절의 태그가 연속으로 나올경우 문장을 붙인다.
				for (int j = 0; j < tags.length - 1; j++) {
					if (tags[j].equals(tags[j+1])) {
						morphemes[j+1] = morphemes[j] + morphemes[j+1];
					} else {
						tagList.add(tags[j]);
						morphemeList.add(morphemes[j]);
					}
				}
				tagList.add(tags[tags.length - 1]);
				morphemeList.add(morphemes[morphemes.length - 1]);
			
				eojeolSet[i] = new Eojeol(morphemeList.toArray(new String[0]), tagList.toArray(new String[0]));
			}
		}
		
				
		for (int i = 0; i < eojeolSet.length; i++) {
			morphemes = eojeolSet[i].getMorphemes();
			tags = eojeolSet[i].getTags();
			SPMorphemes.clear();
			SPTags.clear();
			
			for (int j = 0; j < tags.length; j++) {
				//char c = tags[j].charAt(0);
				String c = tags[j];
				if (c.equals("NC")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}else if (c.equals("PV")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}else if (c.equals("PA")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}else if (c.equals("MA")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add(tags[j]);
				}else if (c.equals("F")) {
					SPMorphemes.add(morphemes[j]);
					SPTags.add("NC");
				}
			}
			
			eojeolSet[i].setMorphemes(SPMorphemes.toArray(new String[0]));
			eojeolSet[i].setTags(SPTags.toArray(new String[0]));
		}
		
		st.setEojeols(eojeolSet);
		
		return st;
	}
}
