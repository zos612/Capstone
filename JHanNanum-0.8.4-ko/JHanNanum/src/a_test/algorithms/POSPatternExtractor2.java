package a_test.algorithms;

import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

/**
 * 이 클래스는 패턴과 일치하는지 확인하고 출력합니다.
 * NV패턴만 구현하였음 -테스트중
 */

public class POSPatternExtractor2 {
	/** the buffer for noun morphemes */
	private LinkedList<String> Morphemes = null;
	
	/** the buffer for tags of the morphemes */
	private LinkedList<String> Tags = null;
	
	POSPatternExtractor2(){
		Morphemes = new LinkedList<String>();
		Tags = new LinkedList<String>();
	}
	
	public void analyze(Eojeol[] eojeolArray, int i){
		String[] morphemes = eojeolArray[i+1].getMorphemes();
		String[] tags = eojeolArray[i+1].getTags();
		Morphemes.clear();
		Tags.clear();
		
		for (int j = 0; j < morphemes.length; j++){
			//NV패턴인지 확인
			if(tags[j].equals("PA")||tags[j].equals("PV")){ //tags[j].charAt(0) == 'P'
				Morphemes.add(morphemes[j]);
				Tags.add(tags[j]);
				//NV패턴 출력
				System.out.print("{ ");
				for (int k = i; k < i+2; k++) {
						//어절 배열에 있는 단어를 하나 씩 출력						
						morphemes = eojeolArray[k].getMorphemes();
						tags = eojeolArray[k].getTags();
						Morphemes.add(morphemes);
						Tags.add(tags[j]);
						
				}
				System.out.print("} ");
			}
		}
		eojeolSet[i].setMorphemes(Morphemes.toArray(new String[0]));
		eojeolSet[i].setTags(Tags.toArray(new String[0]));
	}
}
