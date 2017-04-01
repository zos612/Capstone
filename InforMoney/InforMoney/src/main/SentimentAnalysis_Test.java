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

package main;

import java.util.LinkedList;

import algorithms.*;
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
public class SentimentAnalysis_Test {
	///
	public static void main(String[] args) {
		
		OpinionMiningProcess opinionMining = null;
		
		DictionaryInit dictionaryInit = null;
		
		opinionMining = new OpinionMiningProcess();
		
		dictionaryInit = new DictionaryInit();
		
		
				//Dictionary sentimentDic = new Dictionary();
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_22_AND_EXTRACTOR);

		try {
			
			dictionaryInit.init();
			
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			
			/* Analysis using the work flow */
			String document = "디자인이 않예쁘다.안녕하세요.디자인이 정말 예뻐요.이 물건은 배송이 빨라서 정말 좋네요.소리가 너무 커요.가습량이 많지 않네요.분무량이  많아요.\n";//소리가 작아서 좋아요
			//String document = "가습량이 너무 만족함. 가습량이 많아요. 분무량이  많지 않아요.\n";
			//String document = "가습기 특성상 청소 및 관리(필터식) 등 신경써야하는 부분이 많고, 디자인이 예뻐요. 제품 크기에 따라 장소 제한도 많아 상당히 많은 제품을 비교하다가 우연히 찾은 작은 소형 가습기가 있어서 상품특성 등 살펴보고 구입을 해봤는데.. 정말 가격대비나 효율성면에서 저에게 딱 맞는 제품인것 같습니다. 사이즈는 작은 물컵에 들어갈 정도로 정말 작은데, 가습력은 왠만한 미니가습기랑 동급수준이고 소음이 다소 있는데, 정말 조용한 밤이 아닌 이상 신경쓰일 정도는 아닙니다. 동봉되어 있는 소음 방지용 필터를 끼우면 정말 조용하네요. 일단 별도의 수조통이 없이 아무 컵이든 그릇이든 물이 있는 곳이라면 이 제품을 물위에 띄우면 바로 가습기가 되니 관리가 수월하네요. 전원 공급방식도 소형이지만, USB부터 일반 전원코드까지 다 지원되고...케이블 길이도 적당하고 더 길게 원하면 USB 연장 케이블로 연장하여 사용하면 될 것 같고요. 조만간 하나 더 구입하려고 합니다. ^^";

			workflow.analyze(document);
			
			//sentimentDic.readDic(".\\data\\kE\\dic_sentiment.txt");
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			
			opinionMining.process(resultList);
			
			workflow.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow.close();  	
	}
}