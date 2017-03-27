package a.algorithms;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;

/** 
 * 이 클래스는 문장에서 속성명을 추출하고 
 * 속성명과 인접한 곳에서 의견 단어를 추출합니다.
 * 
 */
public class FeatureExtractor {
	
	public PatternExtractor patternExtractor = null;
	
	//public SentimentEojeol se = null;
	
	public FeatureExtractor(){
		 patternExtractor = new PatternExtractor();
	}
	
	public void extract(Sentence s){
	
		String[] feature = { "소음", "소리", "가습량", "분무량", "분사량", "디자인"};
		//어절을 각배열에 넣는다.
		Eojeol[] eojeolArray = new Eojeol[10];
		eojeolArray = s.getEojeols();
		//속성이 들어있는 문장을 찾는다
		loop1 :
		for(int i = 0; i < feature.length; i++){
			for (int j = 0; j < eojeolArray.length; j++) {
				String[] morphemes = eojeolArray[j].getMorphemes();
				for (int k = 0; k < morphemes.length; k++){
					//속성과 문장속 단어가 일치
					if(feature[i].equals(morphemes[k])){
					//패턴분석
						patternExtractor.extractor(eojeolArray,j);
						break loop1;
					}
				}
			}
			//문장과 속성이 일치하지 않은 경우 다음 문장을 찾는다.
			if(i == feature.length - 1)
				return;
		}
	}	
}
