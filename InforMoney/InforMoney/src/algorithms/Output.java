package algorithms;

import java.util.ArrayList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

public class Output {
	
	MySQLConn db;
	
	Eojeol[] eojeol = null;
	String seFeature = null;
	String seSentWord = null;
	public int seSentValue = 0;
	String word1 = null;
	String word2 = null;
	String word3 = null;
	String word4 = null;
	
	public Output(){
		db = new MySQLConn();
	}
	
	public void output(ArrayList<SentimentEojeol[]> resultList){
		SentimentEojeol[] seArray;
		int rsSize;
		int seLength;
		int posSum = 0;
		int netSum = 0;
		int negSum = 0;
		rsSize = resultList.size();
		
		System.out.println();
				System.out.println("감정 분석 결과 ");
				System.out.println("특징          / 감정단어     / 감정수치     / 어절");
		for(int cnt = 0 ; cnt < rsSize ; cnt++){
			seArray = resultList.get(cnt);
			seLength = seArray.length;
			for(int i = 0; i < seLength ; i++){
			if(seArray[i].getEojeols() != null){
				eojeol = seArray[i].getEojeols();
				seFeature = seArray[i].getSeFeature();
				seSentWord = seArray[i].getSeSentMorph();
				seSentValue = seArray[i].getSentiment();
				
				if(seSentValue == 1)
					posSum += 1;
					else if(seSentValue == 2){
						netSum += 1;
					}else if(seSentValue == -1){
						negSum += 1;
					}
				
				System.out.print(seFeature);
				System.out.print("\t");
				System.out.print(seSentWord);
				System.out.print("\t");
				System.out.print(seSentValue);
				System.out.print("\t");
				
				for (int k = 0; k < 4; k++) {
					if(eojeol[k].getMorphemes() == null)
						break;
					String morpheme = eojeol[k].getMorpheme(0);
					if(morpheme!=null){
						System.out.print(morpheme);
						
							switch(k){
							case 0:
								word1 = morpheme;
								break;
							case 1:
								word2 = morpheme;
								break;
							case 2:
								word3 = morpheme;
								break;
							case 3:
								word4 = morpheme;
								break;
							}
						}
						
					}
				//db.conn(seFeature, seSentWord, seSentValue, word1, word2, word3, word4);
				
				System.out.println();
				}
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