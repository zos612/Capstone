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

import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
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
public class WorkflowNounExtractor {

	public static void main(String[] args) {
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_NOUN_EXTRACTOR);
		
		try {
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			
			/* Analysis using the work flow */
			String document = "같이.이 물건은 배송이 빨라서 정말 좋네요.구매자는 남자고요 여름에 가습기를 구매했었습니다.hi my name is yong\n";
			workflow.analyze(document);
			
			/*String sentence = workflow.getResultOfSentence();
			System.out.println(sentence);*/ //명사만 분석되서 나옴
			//링크드 리스트를 만들어 문장 첫단어를 리스트에 넣는다
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			//s는 각문장
			for (Sentence s : resultList) {
				//어절을 각배열에 넣는다.
				Eojeol[] eojeolArray = s.getEojeols();
				for (int i = 0; i < eojeolArray.length; i++) {
					//어절 배열에 단어가 들어 있는지 체크
					if (eojeolArray[i].length > 0) {
						//어절 배열에 있는 단어를 하나 씩 출력
						String[] morphemes = eojeolArray[i].getMorphemes();
						String[] tags = eojeolArray[i].getTags();
						for (int j = 0; j < morphemes.length; j++) {
							System.out.print(morphemes[j]);
							System.out.print("/");
							System.out.print(tags[j]);
						}
						System.out.print(", ");
					}
				}
			}
			
			/* Once a work flow is activated, it can be used repeatedly. */
			//document = "프로젝트 전체 회의.\n"
				//+ "회의 일정은 다음과 같습니다.\n"
				//+ "日時: 2010년 7월 30일 오후 1시\n"
				//+ "場所: Coex Conference Room\n";
			
		/*	workflow.analyze(document);
			
			resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			for (Sentence s : resultList) {
				Eojeol[] eojeolArray = s.getEojeols();
				for (int i = 0; i < eojeolArray.length; i++) {
					if (eojeolArray[i].length > 0) {
						String[] morphemes = eojeolArray[i].getMorphemes();
						for (int j = 0; j < morphemes.length; j++) {
							System.out.print(morphemes[j]);
						}
						System.out.print(", ");
					}
				}
			}
			System.out.println();
			*/
			workflow.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow.close();  	
	}
}