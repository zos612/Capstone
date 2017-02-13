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

package a_test;

import java.util.LinkedList;

import a_test.algorithms.FeatureExtractor;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;

/**
 * This is a demo program of HanNanum that helps users to utilize the HanNanum library easily.
 * It uses a predefined work flow for noun extracting, which extracts only the nouns in the
 * given document. <br>
 * <br>
 * It performs noun extraction for a Korean document with the following procedure:<br>
 * 		1. Create a predefined work flow for morphological analysis, POS tagging, and noun extraction.<br>
 * 		2. Activate the work flow in multi-thread mode.<br>
 * 		3. Analyze a document that consists of several sentences.<br>
 * 		4. Print the result on the console.<br>
 * 		5. Repeats the procedure 3~4 with activated work flow.<br>
 * 		6. Close the work flow.<br>
 * 
 * @author Sangwon Park (hudoni@world.kaist.ac.kr), CILab, SWRC, KAIST
 */
public class SentimentAnalysis_Test {
	
	public static void main(String[] args) {
		
		FeatureExtractor featureExtractor = null;
		featureExtractor = new FeatureExtractor();
		
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_22_AND_EXTRACTOR);

		try {
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			
			/* Analysis using the work flow */
			String document = "예쁘다.안녕하세요.디자인이 예뻐요.이 물건은 배송이 빨라서 정말 좋네요.소리가 작아서 좋아요.소리가 너무 커요.가습량이 많지 않네요.분무량이  많아요.\n";
			
			workflow.analyze(document);
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			
			for (Sentence s : resultList) {
				featureExtractor.extract(s);
			}
			
			workflow.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow.close();  	
	}
}