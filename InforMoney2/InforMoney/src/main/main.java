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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import algorithms.*;
import database.ModelCheck;
import database.NeutralDB;
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
public class main {
	
	public static void main(String[] args) {
		
		OpinionMiningProcess opinionMining = null;
		
		DictionaryReader dictionaryReader = null;
		
		//SentimentDocument sentDoc = null;
		
		Output2 output = null;
		
		NeutralDB neutralDB = new NeutralDB();
		
		ModelCheck modelCheck = new ModelCheck();

		dictionaryReader = new DictionaryReader();
		
		//sentDoc = new SentimentDocument();
		
		output = new Output2();
		

		/*ArrayList<SentimentEojeol[]> resultList = null;
		
		resultList = new ArrayList<SentimentEojeol[]>();*/
		
		ArrayList<ArrayList<SentimentEojeol>> resultList = null;
		
		resultList = new ArrayList<ArrayList<SentimentEojeol>>();
		
		SentimentEojeol[] sentDoc = null;
		
		BufferedReader br = null;

		FileInputStream fis = null;
		InputStreamReader isr = null;
		
		String str;
		
		//neutralDB.neutralDB();
		
		String path="reviews\\";
				File dirFile=new File(path);
				File []fileList=dirFile.listFiles();
				for(File tempFile : fileList) {
				  if(tempFile.isFile()) {
				    String tempPath=tempFile.getParent();
				    String tempFileName=tempFile.getName();
				    System.out.println(tempPath+"\\\\"+tempFileName);
				    //System.out.println("FileName="+tempFileName);
				    /*** Do something withd tempPath and temp FileName ^^; ***/
				  }
				}
				
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_22_AND_EXTRACTOR);
		
		String orgUrl = null;
		try {
			//String model = "대우어플라이언스 DEH-C450";
			String model = "제닉스 IN-EAR 인이어";
			//String model = "캔스톤 LX-C4 시그니처";
			//String model = "대우어플라이언스 DEH-C450";
			//String model = "test";
			
			
			String categoryCurrent = "이어폰";
			
			//modelCheck.search(model);
			/*if(modelCheck.search(model) == true){
				System.err.println("등록된 모델입니다.");
				System.exit(0);
			} else {
				modelCheck.insert(model);
			}*/
				

			fis = new FileInputStream("reviews\\"+ model + ".txt");
			//isr = new InputStreamReader(fis, "MS949");
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			
			dictionaryReader.init();
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			
				ArrayList<SentimentEojeol> seArray = new ArrayList<SentimentEojeol>();

			
			
			while((str = br.readLine()) != null){
				if (str.equals("")) {
					continue;
				}else if(str.length() <= 2){
					continue;
				}
				
				String document = str;
			
				workflow.analyze(document);
				
				System.out.print("리뷰내용 : ");
				
				System.out.println(document);
				
				LinkedList<Sentence> sentenceList = workflow.getResultOfDocument(new Sentence(0, 0, false));
				//Sentence sentenceList = workflow.getResultOfSentence(new Sentence(0, 0, false));
				
				
				opinionMining = new OpinionMiningProcess();
				
				resultList.add(opinionMining.readSentence(sentenceList, categoryCurrent));
				
				opinionMining = null;
				
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
	