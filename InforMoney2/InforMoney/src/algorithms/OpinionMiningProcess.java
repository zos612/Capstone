package algorithms;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;

import backup.SentimentAnalyzer_back;

/** 
 * 이 클래스는 문장에서 속성명을 추출하고 
 * 속성명과 인접한 곳에서 의견 단어를 추출합니다.
 * 
 */
public class OpinionMiningProcess {
	public SentimentAnalyzer_back patternAnalyzer = null;
	//public SentimentAnalyzer patternAnalyzer = null;
	
	public SentimentEojeol se = null;
	
	public SentimentEojeol seTmp = null;
	
	public SentimentDocument sentDoc = null;
	
	public ArrayList<SentimentEojeol> seArray = null;
	
	public int seNum = 0;
	
	public int sentiment = 0;
	
	String morpheme = null;
	
	String[] tag = null;
	
	Eojeol[] eojeol = null;//new Eojeol[30];
	
	String seFeature = null;
	
	String seSentWord = null;
	
	public String seSentence = null;
	
	private Eojeol[] tmpEojeol = null;
	
	public static int extCnt = 0;
	
	public int eojeolArraySize = 0;
	
	public static boolean analyzing = true;
	
	private String categoryCurrent = null;
	
	
	
	public OpinionMiningProcess(){
		patternAnalyzer = new SentimentAnalyzer_back();
		//patternAnalyzer = new SentimentAnalyzer();
		sentDoc = new SentimentDocument();
	}
	
	/**
	 * 형태소 분석이 된 문장들을 한 문장씩 불러옵니다. 
	 */
	public ArrayList<SentimentEojeol> readSentence(LinkedList<Sentence> sentenceList, String categoryCurrent){
		//SentimentDocument 자료형 임시
		
		this.categoryCurrent = categoryCurrent;
		
		//어절과 감정수치를 담는 배열의 크기를 할당하기위해 (전체 문장의 어절 수 / 5)만큼 배열의 크기를 할당한다.
		for (Sentence s : sentenceList) {
			eojeolArraySize += s.getEojeols().length;
		}
		
		// +1은 배열 크기가 3보다 작은 경우 eojeolArraySize가 0이 되는것을 방지
		eojeolArraySize = ( eojeolArraySize / 3 ) + 1;
	
			for (Sentence s : sentenceList) {
				//System.out.println("테스트 : " + s.getPlainEojeols().toString());
				featureExtract(s);
			}
			if(seArray != null)
			output();
			
			//sentDoc.setSentDoc(seArray);
		
			//seArray = null;
			
		//return sentDoc;
			return seArray;
	}
	
		public void featureExtract(Sentence s){
			//가습기
			//String[] feature = {"가격", "가격대", "사용법", "조절", "사용", "제품" , "조작", "소음", "소리", "성능", "가성비", "가습량", "분무량", "분사량", "디자인", "가습" ,"가습력", "모양", "청소", "관리", "세척", "색깔", "기능", "용량", "내구성"};
			//노트북	
			/*String[] feature = {
					"디자인","외관","속도", "가격", "무게", "색상", "색", "크기", "사이즈", "화면", "설치", "휴대성", "휴대", "화질", "사용", "사용성", "퀄리티", "품질", "키보드", "키감", "메모리", "사양", "소리", "소음"
			};*/
			//스피커
			//String[] feature = { "음질", "소리", "음량", "잡음", "음색", "저음", "사운드", "디자인", "제품", "상품", "가성비", "가격대비", "기능", "출력", "성능", "가격", "가격대", "조절", "사용법", "조작", "설정", "방법", "색", "색상", "색깔", "퀄리티", "품질", "음량", "배터리", "사용", "충전", "음향", "무게" };
			//이어폰
			String[] feature = { "음질", "음색", "노이즈", "소리", "저음", "고음", "가격대비", "가격", "가성비", "디자인", "성능", "색상", "색", "색깔", "재질", "품질", "내구성", "퀄리티", "착용감", "차음성", "귀", "사용"};
			
			Eojeol[] eojeolArray;
			String sentence;
			
			//어절을 각배열에 넣는다.
			eojeolArray = s.getEojeols();
			
			sentence = s.getSentence();
		
			//속성이 들어있는 문장을 찾는다
			for(int i = 0; i < feature.length; i++){
				for (int fIndex = 0; fIndex < eojeolArray.length; fIndex++) {
					String[] morphemes = eojeolArray[fIndex].getMorphemes();
					for (int k = 0; k < morphemes.length; k++){
						//속성과 문장속 단어가 일치
						if(feature[i].equals(morphemes[k])){
						//패턴분석 , 패턴분석 결과를 se객체에 리턴한다.
							//while(analyzing){
							seArray = patternAnalyzer.patternAnalyze(eojeolArray, fIndex, categoryCurrent, sentence);
							
						}
					}
				}
				//문장과 속성이 일치하지 않은 경우 다음 문장을 찾는다.
				if(i == feature.length - 1)
					return;
			}
		}

		/**
		 * 어절과 수치만을 따로 분리하여 출력할 수 있도록 하였다. 여기서  mysql과 연동 하면 될듯 하다.
		 */
		public void output(){
//			try{
//			FileWriter fileTest = new FileWriter("C:\\Users\\yong\\Desktop\\test\\analysis_output.txt");
			System.out.println();
			System.out.println("어절 및 수치출력 : ");
			System.out.println("특징          / 감정단어      / 감정수치      / 어절");
			
				for(int i = 0; i < seArray.size(); i++ ){
				/*if(seArray[i].getEojeols() == null){
					break;
				}else if(seArray[i].getEojeols()[0] == null){
					break;
				}*/
					seTmp = seArray.get(i);
					eojeol = seTmp.getEojeols();
					seFeature = seTmp.getSeFeature();
					seSentWord = seTmp.getSeSentMorph();
					sentiment = seTmp.getSentiment();
					seSentence = seTmp.getSentence();
					
					
					System.out.print(seFeature);
					System.out.print("\t");
					System.out.print(seSentWord);
					System.out.print("\t");
					System.out.print(sentiment);
					System.out.print("\t");
	
				for (int k = 0; k < 4; k++) {
					if(eojeol[k].getMorphemes() == null)
						break;
						
					morpheme = eojeol[k].getMorpheme(0);
					
					if(morpheme!=null){
					System.out.print(morpheme);
	//				fileTest.write(morpheme);
	//				fileTest.write(" ");
					}
				}
				
				System.out.print("\t\t");
				System.out.print(seSentence);
				
				System.out.println();
				//System.out.print("  ");
				//fileTest.write(strSent);
				//fileTest.write("\t");
				
			}
			
			/*
		fileTest.close();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}*/
		
		//긍정, 중립, 부정 수치를 각각 합하여 점수로 보여준다.
		int posSum = 0;
		int netSum = 0;
		int negSum = 0;
		if(seArray != null){
			for(int i = 0; i < seArray.size() ; i++){
				sentiment = seTmp.getSentiment();
				if(sentiment == 1)
				posSum += 1;
				else if(sentiment == 2){
					netSum += 1;
				}else if(sentiment == -1){
					negSum += 1;
				}
			}
		}
		//seTmp = null;
		
		System.out.print("긍정점수  : ");
		System.out.print(posSum);
		System.out.print(" ");
		System.out.print("중립점수  : ");
		System.out.print(netSum);
		System.out.print(" ");
		System.out.print("부정점수  : ");
		System.out.println(negSum);
		System.out.println();
	}
}
