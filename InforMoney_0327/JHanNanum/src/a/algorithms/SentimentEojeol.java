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

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

/**
 * This class represents an Eojeol for internal use. An eojeol consists
 * of more than one umjeol, and each eojeol is separated with spaces.
 * Korean is a agglutinative language so lexemes in an eojeol may be inflected. 
 * 
 * @author Sangwon Park (hudoni@world.kaist.ac.kr), CILab, SWRC, KAIST
 */
public class SentimentEojeol {
	/**
	 * The number of eojeols in this eojeol.
	 */
	public int length = 0;
	
	/**
	 * eojeols in the eojeol.
	 */
	public Eojeol[] eojeols = null;
	
	/**
	 * Eojeol sentiment of each morpheme.
	 */
	public int sentiment = 0;
	
	/**
	 * Constructor.
	 */
	public SentimentEojeol() {
	}
	
	/**
	 * Constructor.
	 * @param eojeols - array of eojeols
	 * @param sentiment - tag array for each morpheme
	 */
	
	 public SentimentEojeol(Eojeol[] eojeols, int sentiment) {
		this.eojeols = eojeols;
		this.sentiment = sentiment;
		
	}
	
	
	/**
	 * It returns the morpheme list in the eojeol.
	 * @return morpheme list for this eojeol
	 */
	public Eojeol[] getEojeols() {
		return eojeols;
	}
	
	/**
	 * It returns the morpheme on the specific index.
	 * @param index - index of morpheme
	 * @return the morpheme on the index
	 */
	public Eojeol getEojeol(int index) {
		return eojeols[index];
	}
	
	/**
	 * Set the morpheme list with a morpheme array.
	 * @param eojeols - array to set the eojeols
	 */
	
	
	public void setEojeols(Eojeol[] eojeols) {
		this.eojeols = eojeols;
	}
	
	
	/**
	 * Set a morpheme on the specific position
	 * @param index - position of the morpheme to change
	 * @param morpheme - new morpheme for the index
	 * @return index: when the morpheme was set up correctly, otherwise -1
	 */
	/*public int setEojeol(int index, Eojeol eojeol) {
		if (index >= 0 && index < eojeols.length) {
			eojeols[index] = eojeol;
			return index;
		} else {
			return -1;
		}
	}
	*/
	public void setEojeol(Eojeol[] eojeol) {
			eojeols = eojeol;
	}
	/**
	 * It returns the tag list for the eojeols in the eojeol.
	 * @return sentiment list for eojeols
	 */
	public int getSentiment() {
		return sentiment;
	}
	
	/**
	 * It returns the tag of the morpheme on the given position.
	 * @param index - the position of the morpheme to get its tag
	 * @return morpheme tag on the given position
	 */
	/*public int getSentiment(int index) {
		return sentiment[index];
	}*/
	
	/**
	 * It sets the tag list for the eojeols of the eojeol.
	 * @param sentiment - new sentiment list for the morpheme list
	 */
	public void setSentiment(int sentiment) {
		this.sentiment = sentiment;
	}
	
	/**
	 * It changes the tag of the morpheme on the index
	 * @param index - position of the morpheme to change its tag
	 * @param tag - new morpheme tag
	 * @return index: the new tag was set up correctly, otherwise -1
	 */
	/*
	 * public int setSentiment(int index, int sentiment) {
		if (index >= 0 && index < sentiment.length) {
			sentiment[index] = sentiment;
			return index;
		} else {
			return -1;
		}
	}*/

	/**
	 * It returns a string that represents the eojeol with eojeols and sentiment.
	 * For example, 나/npp+는/jxc.
	 */
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < length; i++) {
			if (i != 0) {
				str += "+";
			}
			str += eojeols[i] + "/" + sentiment;
		}
		return str;
	}
}
