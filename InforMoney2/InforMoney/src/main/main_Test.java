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

import java.io.IOException;
import java.util.ArrayList;
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
import test.OpinionMiningProcess2;

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
	
	public static void main(String[] args) {
		
		OpinionMiningProcess opinionMining = null;
		
		DictionaryReader dictionaryReader = null;
		
		//SentimentDocument sentDoc = null;
		
		Output2 output = null;
		
		
		
		dictionaryReader = new DictionaryReader();
		
		//sentDoc = new SentimentDocument();
		
		output = new Output2();
		

		/*ArrayList<SentimentEojeol[]> resultList = null;
		
		resultList = new ArrayList<SentimentEojeol[]>();*/
		
		ArrayList<ArrayList<SentimentEojeol>> resultList = null;
		
		resultList = new ArrayList<ArrayList<SentimentEojeol>>();
		
		SentimentEojeol[] sentDoc = null;
		
		
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_22_AND_EXTRACTOR);
		
		String orgUrl = null;
		try {

			//원하는 네이버쇼핑 리뷰 url입력
			orgUrl = "http://shopping.naver.com/detail/detail.nhn?nv_mid=6726447005&section=review";
			//Document doc = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid=6726447005&section=review").get();//timeout(60000).
			//Document doc = Jsoup.connect("url").get();
			
			//지정한 url에서 select하여 원하는 데이터만 추출한다. 참고로 div.atc는 
			
			
			dictionaryReader.init();
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			
			int cntPage;
			int cnt;
			/*int arrayCnt = 0;
			for(cntPage = 0 ;cntPage < 2 ;cntPage++){
				for(cnt = 0; cnt < 19; cnt++){
					arrayCnt++;
				}
			}
*/				ArrayList<SentimentEojeol> seArray = new ArrayList<SentimentEojeol>();

			String model = "네오티즌 포그링 개인용 미니 USB 청정가습기";
			String categoryCurrent = "가습기";
			for(cntPage = 1;cntPage < 5 ; cntPage++){
				String url = orgUrl + "&page=" + cntPage;
				Document doc = Jsoup.connect(url).get();
				Elements titles = doc.select("div.atc");
			for(cnt = 0; cnt < 5; cnt++){
				
				
			//선택한 url의 n번째 리뷰를 가져온다
				Element e = titles.get(cnt);

				/* Analysis using the work flow */
				
				String document = e.text();
			
				//8패턴 테스트 문장`
				//String document = "디자인이 예쁘다. 디자인이 정말 예쁘다. 디자인이 정말 만족함 . 디자인이 완전 만족. 예쁜 디자인. 세련된 디자인. 너무 예쁜 디자인. 정말 세련된 디자인. 일단 제품은 괜찮아보이고. 디자인 완전 예쁘다. 가격이 착해서 좋다. 예쁜 디자인이 좋다. 예쁜 디자인 색깔 ";
				//String document = " 예쁜 디자인 색깔.가격이 착해서 좋다.예쁜 디자인이 좋다. 디자인이 정말 예쁘다. 디자인이 정말 만족함 . 디자인이 완전 만족. 예쁜 디자인. 세련된 디자인. 너무 예쁜 디자인. 정말 세련된 디자인. 일단 제품은 괜찮아보이고. 디자인 완전 예쁘다. ";

				//String document = "소음 크다. 사용법이 어렵다";
				//String document = "1~5단계 버튼을 누르는 게 아니라 휠로 돌리는 거예요.그레서 미세하게도 조정가능하지만 그냥 최소로 줄이거나 / 중간이거나 / 완전 최대로 틀거나그렇게 3가지로 사용할 거 같아요. 현재 50평 공간에서 가습을 하고 있는데 공간이 넓다보니8버전 사려고 했거든요 시간당 500cc그런데 너무 비싸써...^^;그래서 7버전 샀는데 그래도 분부량이 많아요.그렇다고 공간 전체가 다 촉촉하게 되는 건 아니겠지만3시간 정도 지나서 공간 전체가 그리 건조하지 않다고 느껴지면 그걸로 된거라고 생각합니다.한 군데서 가습하는데 어떻게 완벽하게 집안 전체가 가습이 되겠어요. 그럴려면 선풍기 틀어야지. 아직 그렇게 건조하진 않지만 점점 겨울이 다가오네요.완벽 세척 가습이 미로 가습이 적극 추천합니다. 가격이 좀 비싼건 함ㅋ정ㅋ그러나 미로샵에서는 더 저렴하게 구입 가능:-) 감사합니다. 아래 사진은 분무량 최대일 때 찍은 사진입니다.";
				workflow.analyze(document);
				
				System.out.print("리뷰내용 : ");
				
				System.out.println(document);
				
				LinkedList<Sentence> sentenceList = workflow.getResultOfDocument(new Sentence(0, 0, false));
				
				//분석 결과를 참조
				//seArray = opinionMining.readSentence(sentenceList, categoryCurrent);
				//seArray = opinionMining.readSentence(sentenceList, categoryCurrent).getSentDoc();
				//sentDoc.setSentDoc(opinionMining.readSentence(sentenceList, categoryCurrent)); 
				
				//sentDoc.setSentDoc(seArray);
				//resultList.add(sentDoc.getSentDoc());
				
				opinionMining = new OpinionMiningProcess();
				
				resultList.add(opinionMining.readSentence(sentenceList, categoryCurrent));
				
				//seArray.clear();
				opinionMining = null;
				}
			}
			output.output(resultList , model);
			
			workflow.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow.close();  	
	}
	
	/*void getProductReview(String url) throws IOException{
			
	    	int cnt1;
	    	int cnt2=1;
	    	
	    	
	    	for(cnt1 = 1; cnt1<=100 ; cnt1++){
	    		url = url + "&page="+cnt1;
			Document doc = Jsoup.connect(url).get();
			//Document doc_query = Jsoup.connect(query).get();
			
			Elements scraping = doc.select("div.atc");
			
			for(Element e : scraping){
			System.out.println(cnt2+e.text());
			cnt2++;
			}
		
    	}
    	
	}*/
}
	