package algorithms;

import java.util.LinkedList;

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
	
	public void process(LinkedList<Sentence> resultList){
		
		seArray = new SentimentEojeol[resultList.size()];
		for( int i = 0 ; i < resultList.size(); i++){
			seArray[i] = new SentimentEojeol( null, 0 );
		}
		for (Sentence s : resultList) {
			featureExtract(s);
		}
		output();
	}
		public void featureExtract(Sentence s){
			
			String[] feature = {"배송", "화질", "가격", "소음", "소리", "가습량", "분무량", "분사량", "디자인"};
			//String[] feature = {"가습량", "분무량"};

			//어절을 각배열에 넣는다.
			Eojeol[] eojeolArray; //= new Eojeol[10];
			eojeolArray = s.getEojeols();
			//속성이 들어있는 문장을 찾는다
			//loop1 :
			for(int i = 0; i < feature.length; i++){
				for (int fNum = 0; fNum < eojeolArray.length; fNum++) {
					String[] morphemes = eojeolArray[fNum].getMorphemes();
					for (int k = 0; k < morphemes.length; k++){
						//속성과 문장속 단어가 일치
						if(feature[i].equals(morphemes[k])){
						//패턴분석
							se = patternAnalyzer.patternAnalyze(eojeolArray,fNum);
							if(se != null){
								tmpEojeol = new Eojeol[se.length+1];
								for(int a=0; a < se.length+1; a++){
								tmpEojeol[a] = new Eojeol(null,null);
							}
								//tmpEojeol[0].setMorpheme(0, (se.getEojeol(0).getMorpheme(0)));
								for(int a = 0; a < se.length; a++){
								tmpEojeol[a].setMorphemes(se.getEojeol(a).getMorphemes());
								tmpEojeol[a].setTags(se.getEojeol(a).getTags());
								}
								//tmpSentiment = se.getSentiment();
								seArray[seNum].setEojeols(tmpEojeol);
								seArray[seNum++].setSentiment(se.getSentiment());
								se = null;
							}
							//break loop1;
						}
					}
				}
				//문장과 속성이 일치하지 않은 경우 다음 문장을 찾는다.
				if(i == feature.length - 1)
					return;
			}
		}	
		public void output(){
			System.out.println();
		for(int i = 0; i < seNum-1; i++ ){
			if(seArray[i] == null)
				break;
				eojeol = seArray[i].getEojeols();
			for (int k = 0; k < 4; k++) {
				if(eojeol[k].getMorphemes() == null)
					break;
					
				morphemes = eojeol[k].getMorphemes();
				
				if(morphemes[0]!=null)
				System.out.print(morphemes[0]);
				
			}
			sentiment = seArray[i].getSentiment();
			System.out.print(sentiment);
		}
		//System.out.println();
	//patternExtractor.extractor(eojeolArray, num);
	}
}
