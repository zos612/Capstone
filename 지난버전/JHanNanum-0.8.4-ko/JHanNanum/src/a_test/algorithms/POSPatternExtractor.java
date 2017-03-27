package a_test.algorithms;

import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

/**
 * 이 클래스는 패턴과 일치하는지 확인하고 출력합니다.
 * NV패턴만 구현하였음 -테스트중
 */

public class POSPatternExtractor {
	
	public void analyze(Eojeol[] eojeolArray, int i){
		String[] morphemes = eojeolArray[i+1].getMorphemes();
		String[] tags = eojeolArray[i+1].getTags();
		for (int j = 0; j < morphemes.length; j++){
			//NV패턴인지 확인
			if(tags[j].equals("PA")||tags[j].equals("PV")){ //tags[j].charAt(0) == 'P'
				//NV패턴 출력
				System.out.print("{ ");
				for (int k = i; k < i+2; k++) {
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
				System.out.print("} ");
			}
		}
	}
}
