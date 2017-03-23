package a_test.algorithms;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

public class PatternExtractor {
	
	public SentimentAnalyzer sentimentAnalyzer = null;
	
	//public SentimentEojeol se = null;
	
	//private int arrayNum = 0;
	
	public PatternExtractor(){
	sentimentAnalyzer = new SentimentAnalyzer();
	}
	
	public void extractor(Eojeol[] eojeolArray, int num){
	String[] morph1 = eojeolArray[num+1].getMorphemes();
	String[] tag1 = eojeolArray[num+1].getTags();
	//String[] morph2 = eojeolArray[num+1].getMorphemes();
	String[] tag2 = eojeolArray[num+1].getTags();
	for (int j = 0; j < morph1.length; j++){
		//NV패턴인지 확인
		if(tag1[j].equals("PA")||tag1[j].equals("PV")){ //tag1[j].charAt(0) == 'P'
			//NV패턴 출력
			sentimentAnalyzer.pattern1(eojeolArray, num);
		}
		else if(tag1[j].equals("MA")||tag2[j].equals("PA")||tag2[j].equals("PV")){
			sentimentAnalyzer.pattern2(eojeolArray, num);
			}
		}
	}
}
