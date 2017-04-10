package algorithms;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;

/** 
 * 이 클래스는 문장에서 속성명을 추출하고 
 * 속성명과 인접한 곳에서 의견 단어를 추출합니다.
 * 
 */
public class OpinionMiningProcess {

	public SentimentAnalyzer patternAnalyzer = null;
	
	public SentimentEojeol se = null;
	
	public SentimentEojeol[] seArray = null;

	public int seNum = 0;
	
	public int sentiment = 0;
	
	String[] morphemes = null;
	
	String[] tags = null;
	
	Eojeol[] eojeol = new Eojeol[30];
	
	private Eojeol[] tmpEojeol = null;
	
	private int tmpSentiment = 0;
	
	public static int extCnt = 0;
	
	public OpinionMiningProcess(){
		patternAnalyzer = new SentimentAnalyzer();
	}
	
	/**
	 * 형태소 분석이 된 문장들을 한 문장씩 불러옵니다. 
	 */
	public void readSentence(LinkedList<Sentence> resultList){
	
		int rSize = resultList.size() * 2;
		
		seArray = new SentimentEojeol[rSize];
		for(int i = 0 ; i < rSize; i++){
			seArray[i] = new SentimentEojeol( null, 0 );
		}
		for (Sentence s : resultList) {
			featureExtract(s);
		}
		output();
	}
		public void featureExtract(Sentence s){
			
			String[] feature = {"가격", "가격대", "사용법", "조절", "사용", "조작", "소음", "소리", "성능", "가성비", "가습량", "분무량", "분사량", "디자인", "가습" ,"가습력","소리", "모양", "청소", "관리", "세척", "색깔", "기능", "용량", "내구성", "향"};

			Eojeol[] eojeolArray;
			
			//어절을 각배열에 넣는다.
			eojeolArray = s.getEojeols();
		
			//속성이 들어있는 문장을 찾는다
			for(int i = 0; i < feature.length; i++){
				for (int fNum = 0; fNum < eojeolArray.length; fNum++) {
					String[] morphemes = eojeolArray[fNum].getMorphemes();
					for (int k = 0; k < morphemes.length; k++){
						//속성과 문장속 단어가 일치
						if(feature[i].equals(morphemes[k])){
						//패턴분석 , 패턴분석 결과를 se객체에 리턴한다.
							se = patternAnalyzer.patternAnalyze(eojeolArray,fNum);
							if(se != null){
								tmpEojeol = new Eojeol[se.length+1];
								for(int a=0; a < se.length+1; a++){
								tmpEojeol[a] = new Eojeol();
							}
								//tmpEojeol에 se의 어절들을 복사
								for(int a = 0; a < se.length; a++){
								tmpEojeol[a].setMorphemes(se.getEojeol(a).getMorphemes());
								tmpEojeol[a].setTags(se.getEojeol(a).getTags());
								}
								//seArray배열에 어절과 감정수치 저장
								seArray[seNum].setEojeols(tmpEojeol);
								seArray[seNum++].setSentiment(se.getSentiment());
								se = null;
							}
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
			try{
			FileWriter fileTest = new FileWriter("C:\\Users\\yong\\Desktop\\test\\analysis_output.txt");
	
			System.out.println();
			System.out.print("어절 및 수치출력 : ");
		for(int i = 0; i < seNum; i++ ){
			if(seArray[i] == null)
				break;
				eojeol = seArray[i].getEojeols();
			for (int k = 0; k < 4; k++) {
				if(eojeol[k].getMorphemes() == null)
					break;
					
				morphemes = eojeol[k].getMorphemes();
				
				if(morphemes[0]!=null){
				System.out.print(morphemes[0]);
				fileTest.write(morphemes[0]);
				fileTest.write(" ");
				}
			}
			sentiment = seArray[i].getSentiment();
			String strSent;
			strSent = Integer.toString(sentiment);
			System.out.print(sentiment);
			System.out.print("  ");
			fileTest.write(strSent);
			fileTest.write("\t");
			
		}
		fileTest.close();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		System.out.println();
		
		//긍정, 중립, 부정 수치를 각각 합하여 점수로 보여준다.
		int posSum = 0;
		int netSum = 0;
		int negSum = 0;
		for(int i = 0; i < seNum ; i++){
			sentiment = seArray[i].getSentiment();
			if(sentiment == 1)
			posSum += 1;
			else if(sentiment == 0){
				netSum += 1;
			}else if(sentiment == -1){
				negSum += 1;
			}
		}
		System.out.print("긍정점수  : ");
		System.out.print(posSum);
		System.out.print(" ");
		System.out.print("중립점수  : ");
		System.out.print(netSum);
		System.out.print(" ");
		System.out.print("부정점수  : ");
		System.out.println(negSum);
	}
}
