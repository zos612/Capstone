package a.algorithms;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

public class PatternExtractor {
	
	public SentimentAnalyzer sentimentAnalyzer = null;
	
	//public SentimentEojeol se = null;
	
	//private int arrayNum = 0;
	public final static int NV_PATTERN = 1;
	public final static int NZV_PATTERN = 2;
	public final static int NZN_PATTERN = 3;
	public int patternNum = 0;
	public PatternExtractor(){
	sentimentAnalyzer = new SentimentAnalyzer();
	}
	
	public void extractor(Eojeol[] eojeolArray, int num){
	String[] morph1 = eojeolArray[num+1].getMorphemes();
	String[] tag1 = eojeolArray[num+1].getTags();
	String[] morph2 = eojeolArray[num+2].getMorphemes();
	String[] tag2 = eojeolArray[num+2].getTags();
	for (int j = 0; j < morph1.length; j++){
		//NV패턴인지 확인
		if(tag1[j].equals("PA")||tag1[j].equals("PV")){ //tag1[j].charAt(0) == 'P'
			//NV패턴 출력
			patternNum = NV_PATTERN;
			sentimentAnalyzer.analyze(eojeolArray, num, patternNum);
		}
		else if(tag1[j].equals("MA")&&(tag2[j].equals("PA")||tag2[j].equals("PV"))){
			patternNum = NZV_PATTERN;
			sentimentAnalyzer.analyze(eojeolArray, num, patternNum);
			}
		else if(tag1[j].equals("MA")&&tag2[j].charAt(0)=='N'){
			patternNum = NZN_PATTERN;
			sentimentAnalyzer.analyze(eojeolArray, num, patternNum);
			}
		}
	}
}
