package a_test.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

public class SentimentAnalyzer {
	public final static int MAX_EOJEOLS = 10000;
	
	/** temporary list for new tags */
	//private ArrayList<Eojeol[]> eojeolList = null;
	
	/** temporary list for morpheme tags */
	//private ArrayList<Integer> sentimentList = null;
	
	private ArrayList<String> morphemeList = null;
	
	/** the buffer for tags of the morphemes */
	private ArrayList<String> tagList = null;
	
	//public SentimentEojeol sentEojeols = new SentimentEojeol();//배열을 없애니까 에러가 없어짐

	public SentimentDic sentDic = null;  
	
	public static SentimentEojeol se = null;
	
	public int newNum =0 ;

	public SentimentAnalyzer(){
		sentDic = new SentimentDic();
		//eojeolList = new ArrayList<Eojeol[]>();
		//sentimentList = new ArrayList<Integer>();
		morphemeList = new ArrayList<String>();
		tagList = new ArrayList<String>();
	}
	
	public int sentimentV; //V : 동사 감정수치
	public int sentimentZ; //Z : 부사 감정수치
	public int sentiment;
	public int arrayNum = 0;
	
	public void pattern1(Eojeol[] eojeolArray, int num){
//		Eojeol e[] = eojeolArray[]
		String[] morphP1 = eojeolArray[num+1].getMorphemes(); 
		String[] tagP1 = eojeolArray[num+1].getTags(); 
		String[] morphP2 = eojeolArray[num+1].getMorphemes(); 
		String[] tagP2 = eojeolArray[num+1].getTags(); 
		String[] morphemes = null;
		String[] tags = null;
		
		Eojeol []newEojeol = new Eojeol[10];
		
		String sentMorph = sentDic.get(morphP1[0]);
		sentimentV = Integer.parseInt(sentMorph);
		
		//eojeolList.add(eojeolArray);
		//sentimentList.add(sentimentV[num]);
		//sentEojeols.setEojeol(eojeol);
		
		System.out.print("{ ");
		for (int k = num; k < num+2; k++) { //k는 문장의 속성 위치
				//어절 배열에 있는 단어를 하나 씩 출력				
			//morphemeList.clear();
			morphemes = eojeolArray[k].getMorphemes();
			tags = eojeolArray[k].getTags();
			morphemeList.add(morphemes[0]);
			tagList.add(tags[0]);
			
	
				for (int l = 0; l < morphemes.length; l++) {
					System.out.print(morphemes[l]);
					System.out.print("/");
					System.out.print(tags[l]);
				}
				System.out.print(", ");	
				//newEojeol[newNum++].setMorpheme(1, morphemeList.get(k));
				//newEojeol[newNum++].setTags(tagList.toArray(new String[0]));
				//newEojeol = new Eojeol(morphemeList.toArray(new String[0]), tagList.toArray(new String[0]));
				
				//sentEojeols.setEojeols(newEojeol);
				//sentEojeols.setSentiment(sentimentV);
				
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
	public void pattern2(Eojeol[] eojeolArray, int num){
		String[] morphP1 = eojeolArray[num+1].getMorphemes();
		String[] tagP1 = eojeolArray[num+1].getTags();
		String[] morphP2 = eojeolArray[num+2].getMorphemes();
		String[] tagP2 = eojeolArray[num+2].getTags();
		String[] morphemes = null;
		String[] tags = null;
		
		//String morphZ = sentDic.get(morphP1[0]);
		String morphV = sentDic.get(morphP2[0]);
		
		//sentimentZ[num] =Integer.parseInt(morphS);
		sentiment =Integer.parseInt(morphV);
		
		//sentiment[num] = sentimentZ[num] * sentimentV[num];
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
		if(sentiment == 1){
			System.out.print("긍정 ");
		}
		else if(sentiment == 0){
			System.out.print("중립 ");
		}
		else if(sentiment == -1){
			System.out.print("부정 ");
		}
	}
}
