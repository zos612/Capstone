package test;
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



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;

/**
 * This is a demo program of HanNanum that helps users to utilize the HanNanum library easily.
 * It uses a predefined work flow for noun extracting, which extracts only the nouns in the
 * given doc. <br>
 * <br>
 * It performs noun extraction for a Korean doc with the following procedure:<br>
 * 		1. Create a predefined work flow for morphological analysis, POS tagging, and noun extraction.<br>
 * 		2. Activate the work flow in multi-thread mode.<br>
 * 		3. Analyze a doc that consists of several sentences.<br>
 * 		4. Print the result on the console.<br>
 * 		5. Repeats the procedure 3~4 with activated work flow.<br>
 * 		6. Close the work flow.<br>
 * 
 * @author Sangwon Park (hudoni@world.kaist.ac.kr), CILab, SWRC, KAIST
 */
public class WorkflowNounExtractor {

	public static void main(String[] args) {
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_NOUN_EXTRACTOR);
		//Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_POS_22_AND_EXTRACTOR);

		
		BufferedReader br = null;
		BufferedWriter bw = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		int cnt = 0;
		String doc;
		try {
			/* Activate the work flow in the thread mode */
			
			String model = "캔스톤 LX-C4 시그니처";

			workflow.activateWorkflow(true);
			
			

			fis = new FileInputStream("reviews\\"+ model + ".txt");
			//isr = new InputStreamReader(fis, "MS949");
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			
			fos = new FileOutputStream("reviews\\"+ model + "_noun.txt");
			osw = new OutputStreamWriter(fos, "euc-kr");
			bw = new BufferedWriter(osw);
			

			/* Analysis using the work flow */
			while((doc = br.readLine()) != null){
				if (doc.equals("")) {
					continue;
				}else if(doc.length() <= 2){
					continue;
				}
				
			workflow.analyze(doc);			
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
								//System.out.print(morphemes[j]);
								bw.write(morphemes[j]);
								bw.flush();
								bw.write(" ");
								bw.flush();
							}
							//System.out.print(", ");
						}
					}
				}
				
			}
			bw.newLine();
			bw.flush();
			System.out.println("추출 끝!");
			br.close();
			 isr.close();
			 fis.close();
			
		
			workflow.close();
			
			
		}catch (FileNotFoundException fnfe){
			System.out.println("파일을 찾을 수 없음");
			
		}catch (IOException ioe){
			
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}finally{
				try{
					br.close();
				 isr.close();
				 fis.close();
				}catch (IOException e) {
				e.printStackTrace();
			}
		}
		/* Shutdown the work flow */
		workflow.close();  	
	}
}