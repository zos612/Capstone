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
		
		opinionMining = new OpinionMiningProcess();
		
		dictionaryReader = new DictionaryReader();
		
		Output output = null;
		
		output = new Output();

		ArrayList<SentimentEojeol[]> resultList = null;
		
		resultList = new ArrayList<SentimentEojeol[]>();
		
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_22_AND_EXTRACTOR);
		
		try {
			//원하는 네이버쇼핑 리뷰 url입력
			//Document doc = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid=6726447005&section=review").get();//timeout(60000).
			Document doc = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid=10348680585&cat_id=50002540&frm=NVSCTAB&query=%EA%B0%80%EC%8A%B5%EA%B8%B0").get();
			
			//Document doc = Jsoup.connect("http://shopping.naver.com/detail/detail.nhn?nv_mid=6004840565&cat_id=50002540&frm=NVSHATC&query=%EA%B0%80%EC%8A%B5%EA%B8%B0").get();
			//지정한 url에서 select하여 원하는 데이터만 추출한다. 참고로 div.atc는 
			Elements titles = doc.select("div.atc");
			
			dictionaryReader.init();
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			
			for(int a = 0; a < 19; a++){
			//선택한 url의 n번째 리뷰를 가져온다
				Element e = titles.get(a);
				 
				/* Analysis using the work flow */
				
				String document = e.text();
			
				//8패턴 테스트 문장
				//String document = "디자인이 예쁘다. 디자인이 정말 예쁘다. 디자인이 정말 만족함 . 디자인이 완전 만족. 예쁜 디자인. 세련된 디자인. 너무 예쁜 디자인. 정말 세련된 디자인. ";
				workflow.analyze(document);
				
				System.out.print("리뷰내용 : ");
				System.out.println(document);
				
				LinkedList<Sentence> sentenceList = workflow.getResultOfDocument(new Sentence(0, 0, false));
				//System.out.println(resultList.getFirst());
				//Sentence resultList = workflow.getResultOfSentence(new Sentence(0, 0, false));
				//System.out.println(resultList);
				
				resultList.add(opinionMining.readSentence(sentenceList));
				
			}
			output.output(resultList);
			
			workflow.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow.close();  	
	}
}
	