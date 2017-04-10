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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class main_Test {
	///
	public static void main(String[] args) {
		
		OpinionMiningProcess opinionMining = null;
		
		DictionaryReader dictionaryReader = null;
		
		opinionMining = new OpinionMiningProcess();
		
		dictionaryReader = new DictionaryReader();

		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_22_AND_EXTRACTOR);
		
		try {
			//원하는 네이버쇼핑 리뷰 url입력
			Document doc = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid=6726447005&section=review").get();
			//지정한 url에서 select하여 원하는 데이터만 추출한다. 참고로 div.atc는 
			Elements titles = doc.select("div.atc");
			
			dictionaryReader.init();
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			
			//선택한 url의 첫번째 리뷰를 가져온다.
			Element e = titles.get(1);
			 
			/* Analysis using the work flow */
			//String document = "정말 저렴한 가격.디자인이 않예쁘다.안녕하세요.디자인이 정말 예뻐요.소리가 너무 커요.가습량이 많지 않네요.분무량이  많아요.\n";//소리가 작아서 좋아요.이 물건은 배송이 빨라서 정말 좋네요.
			//String document = "기침을 많이해서 가습기를 알아보던 중에어느 집 인테리어에서나 잘 어울리는 심플한 디자인이라 구매했어요.가격도 비싸지 않아서 부담 없었는데성능이 별로면 이번 시즌만 끝나고 장식용으로 쓰려고 했어요사용한지 일주일정도 됐는데 작동 중 소음도 아주 적고(신경써서 듣지 않으면 들리지도 않을 정도)가끔씩 물 채워질 때 꿀렁꿀렁 몇초 소리 나다가 다시 조용해져요후기 보니까 좀 사용하면 소음이 난다고 하는데전 사용 초기라서 그런지 아직 모르겠네요.일반적으로 기대하는 가습기 그 자체입니다. 저렴한 가격에 디자인이 돋보여서 만족스러운거죠.밑 바닥에는 아로마오일을 패드에 적시는 곳이 있어요아직 안해봤는데 패드는 기존꺼 1개, 여유분 2개?3개? 있어요패드좀 넉넉히 주시면 더더 좋았을텐데 뭐 그래도 가습기 성능 자체는 만족합니다.다음에 선물할 일 있으면 이거 사줘야겠어요 ㅋㅋ";
			
			String document = e.text();
			//String document = "디자인 이쁜 반면에, 물 교체할때 그립감이 조금 불편하긴 하지만 만족하는 편입니다. 물은 다 채우지 않고 1단 틀었을때 8시간쯤 지속되는듯. 물 없으면 혼자 꺼져 있음. 가습기 처음 써봐서 잘은 모르지만 필터부분은 조금 조잡한 느낌. 퇴근할때 씻어놓고 가는데, 안쪽 관 부분은 구조상 습기가 남아있음. 아직은 깨끗하지만 오래 써보면 문제점이 될수도.. 그런데 역시 초음파 가습기는 공기가 차가워진다는 단점은 어쩔수 없는듯. 그래도 난방기 히터바람이 너무 건조해서 샀는데, 없었을 때랑 차이가 느껴져서 대체로 만족스럽게 쓰고 있습니다. 겨울에는 습도가 많이 중요한듯.";
			//String document = "디자인이 예쁘다. 디자인이 정말 예쁘다. 디자인이 정말 만족함. 디자인이 완전 예쁨. 예쁜 디자인. 세련된 디자인. 너무 예쁜 디자인. 정말 세련된 디자인. ";
			
			workflow.analyze(document);
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			
			opinionMining.readSentence(resultList);
			
			System.out.print("리뷰내용 : ");
			System.out.print(document);
			
			workflow.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow.close();  	
	}
}