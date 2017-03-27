package a.algorithms;

import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;

/** 
 * 이 클래스는 문장에서 속성명을 추출하고 
 * 속성명과 인접한 곳에서 의견 단어를 추출합니다.
 * 
 */
public class OpinionMiningProcess {

	public FeatureExtractor featureExtractor = null;
	
	public PatternExtractor patternExtractor = null;
		
	public SentimentEojeol []seArray = null;
	
	public int seNum = 0;
	
	public int sentiment = 0;
	
	String[] morphemes = null;
	
	String[] tags = null;
	
	Eojeol[] eojeol = new Eojeol[30];
	
	public static int extCnt = 0;
	
	public OpinionMiningProcess(){
		featureExtractor = new FeatureExtractor();
		patternExtractor = new PatternExtractor();
	}
	
	public void process(LinkedList<Sentence> resultList){

		seArray = new SentimentEojeol[resultList.size()];

		for (Sentence s : resultList) {
			featureExtractor.extract(s);
			//이 부분에서 에러발생
			if(SentimentAnalyzer.se != null){
				seArray[seNum++] = SentimentAnalyzer.se;
				SentimentAnalyzer.se = null;
			}
		}
		//System.out.println();
			
		for(int i = 0; i<seNum; i++ ){
			if(seArray[i] == null)
				break;
				eojeol = seArray[i].getEojeols();
			for (int k = 0; k <3; k++) {
				if(eojeol[k].getMorphemes() == null)
					break;
					
				morphemes = eojeol[k].getMorphemes();
				
				if(morphemes[0]==null)
					break;

				System.out.print(morphemes[0]);
				
			}
			sentiment = seArray[i].getSentiment();
			System.out.print(sentiment);
		}
	//patternExtractor.extractor(eojeolArray, num);
	}
}
