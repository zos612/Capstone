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

package kr.ac.kaist.swrc.jhannanum.demo;

import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;

/**
 * This is a demo program of HanNanum that helps users to utilize the HanNanum library easily.
 * It uses a predefined work flow for morphological analysis and POS tagging with 22 morpheme tags,
 * which can be used by users who want simpler POS tagging results. <br>
 * <br>
 * It performs POS tagging for a Korean document with the following procedure:<br>
 * 		1. Create two predefined work flows for comparison between POS tagging with 69 tags and with 22 tags.<br>
 * 		2. Activate the work flows in multi-thread mode.<br>
 * 		3. Analyze a document using each work flow.<br>
 * 		4. Print the result on the console.<br>
 * 		5. Close the work flow.<br>
 * 
 * @author Sangwon Park (hudoni@world.kaist.ac.kr), CILab, SWRC, KAIST
 */
public class WorkflowSimplePos22 {

	public static void main(String[] args) {
		Workflow workflow1 = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_HMM_POS_TAGGER);
		Workflow workflow2 = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_SIMPLE_22);
		
		try {
			/* Activate the work flow in the thread mode */
			workflow1.activateWorkflow(true);
			workflow2.activateWorkflow(true);
			
			/* Analysis using the work flow */
			//String sentence = "화질이 정말 좋아요.이 물건은 배송이 빨라서 정말 좋네요.분무량도 괜찬고 디자인도 예뻐요.";
			String sentence = "가격이 너무 만족. 가습량이 많아요. 분무량이  많지 않아요.\n";
			workflow1.analyze(sentence);
			workflow2.analyze(sentence);
			
			System.out.println("# POS tagging result with 69 tags.\n");
			System.out.println(workflow1.getResultOfSentence());
			
			System.out.println("# POS tagging result with 22 tags.\n");
			System.out.println(workflow2.getResultOfSentence());

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow1.close();  	
		workflow2.close();
	}
}