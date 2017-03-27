package a.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

public class SentimentAnalyzer {
	public final static int MAX_EOJEOLS = 10000;

	public SentimentDic sentDic = null;
	
	public static SentimentEojeol se = null;
	
	public int newNum = 0;

	public SentimentAnalyzer(){
		sentDic = new SentimentDic();
	}
	
	public int sentimentV; //V : 동사 감정수치
	public int sentimentZ; //Z : 부사 감정수치
	public int sentiment;
	public int arrayNum = 0;
	public String sentMorph = null;
	
	public void analyze(Eojeol[] eojeolArray, int num, int patternNum){
		String[] morphP1 = eojeolArray[num+1].getMorphemes(); 
		String[] tagP1 = eojeolArray[num+1].getTags(); 
		String[] morphP2 = eojeolArray[num+2].getMorphemes(); 
		String[] tagP2 = eojeolArray[num+2].getTags(); 
		String[] morphemes = null;
		String[] tags = null;
		
		Eojeol []newEojeol = new Eojeol[10];
		
		if(patternNum == PatternExtractor.NV_PATTERN){
			sentMorph = sentDic.get(morphP1[0]);
			sentimentV = Integer.parseInt(sentMorph);
		}
		else if(patternNum == PatternExtractor.NZV_PATTERN){
			sentMorph = sentDic.get(morphP2[0]);
			sentimentV = Integer.parseInt(sentMorph);
		}
		else if(patternNum == PatternExtractor.NZN_PATTERN){
			sentMorph = sentDic.get(morphP2[0]);
			sentimentV = Integer.parseInt(sentMorph);
		}
		
		System.out.print("{ ");
		for (int k = num; k < num+3; k++) { //k는 문장의 속성 위치
		
			//어절 배열에 있는 단어를 하나 씩 출력
			morphemes = eojeolArray[k].getMorphemes();
			tags = eojeolArray[k].getTags();
			
				for (int l = 0; l < morphemes.length; l++) {
					System.out.print(morphemes[l]);
					System.out.print("/");
					System.out.print(tags[l]);
				}
				System.out.print(", ");	
				
		}
		se = new SentimentEojeol(eojeolArray, sentimentV);
		
		System.out.print("} ");
		System.out.print("-> ");
		
		if(sentimentV == 1){
			System.out.print("긍정 ");
		}
		else if(sentimentV == 0){
			System.out.print("중립 ");
		}
		else if(sentimentV == -1){
			System.out.print("부정 ");
		}
		//return sentEojeols;
	}
}