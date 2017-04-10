package algorithms;

import java.util.Arrays;
import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

public class SentimentAnalyzer {
	public final static int MAX_EOJEOLS = 10000;
	
	public SentimentEojeol se = null;
	
	private LinkedList<String> morphList = null;
	
	private LinkedList<String> tagList = null;
	
	/**
	 * morphM1, morphM2는 각각  feature와 -1, -2만큼 떨어진 어절을 저장한다.
	 * morphP1, morphP2는 각각  feature와 +1, +2만큼 떨어진 어절을 저장한다.
	 * tagM1, tagM2, tagP1, tagP2도 같은 원리다.
	 */
	private String[] morphM1 = new String[1];
	private String[] tagM1 = new String[1];
	private String[] morphM2 = new String[1];
	private String[] tagM2 = new String[1];
	private String[] morphP1 = new String[1];
	private String[] tagP1 = new String[1];
	private String[] morphP2 = new String[1];
	private String[] tagP2 = new String[1];
	
	//private int arrayNum = 0;
	public final static int NV_PATTERN = 1;
	public final static int NZV_PATTERN = 2;
	public final static int NZN_PATTERN = 3;
	public final static int NNN_PATTERN = 4;
	public final static int VN_PATTERN = 5;
	public final static int NN_PATTERN = 6;
	public final static int ZVN_PATTERN = 7;
	public final static int ZNN_PATTERN = 8;


	public int patternNum = 0;

	public int sentValue = 0; //V : 동사 감정수치
	public int sentimentZ; //Z : 부사 감정수치
	public int sentiment;
	
	public int arrayNum = 0;
	public String sentMorph = null;
	
	String[] morphemes = null;
	String[] tags = null;
	
	Eojeol[] eojeolArray;
	Eojeol[] newArray;
	
	int fNum = 0; // feature의 배열 위치
	int mKey = 0; // feature과 minus거리에 따른 키
	int pKey = 0; // feature과 plus거리에 따른 키
	int check = 0; // 감정 분석이 두 번 이상 되지 않도록 체크함. 0이면 감정분석 x 1이면 감정분석 o
	int start; 
	int end;
	String tempMorph = null;
	
	public SentimentAnalyzer(){
		morphList = new LinkedList<String>();
		tagList = new LinkedList<String>();
		
	}
	
	public SentimentEojeol patternAnalyze(Eojeol[] eojeolArray, int fNum){
		
		this.eojeolArray = eojeolArray;
		this.fNum = fNum;
		int arrayEnd = eojeolArray.length - 1;

		//문장의 마지막에서 .으로 끝나지 않는경우 마지막 배열이 null로 끝나지 않아서 값을 불러올 떄 null포인터 에러가 뜬다
		//이를 막기위해 마지막 배열이 null이 아니면 배열의 크기를 1늘려 null인 마지막 배열을 생성한다. 
		if(eojeolArray[arrayEnd].getMorphemes()[0] != null){
			eojeolArray = Arrays.copyOf(eojeolArray, eojeolArray.length + 1);
			eojeolArray[arrayEnd + 1] = new Eojeol(new String[1],null);
			this.eojeolArray = eojeolArray;
		}
		check = 0;
		mKey = 0;
		pKey = 0;
		sentValue = 0;
		morphM1[0] = null;
		morphM2[0] = null;
		morphP1[0] = null;
		morphP2[0] = null;
		tagM1[0] = null;
		tagM2[0] = null;
		tagP1[0] = null;
		tagP2[0] = null;
		
		/**
		 * fNum은 feature의 배열 상의 위치이다.
		 * feature의 위치가 -1이하인 경우 fNum-1위치의 어절이 존재하는지 확인한다. 또한 fNum-1위치의 어절이 null인지 확인 
		 * fNum-1위치의 어절과 태그를 변수에 저장한다.
		 */
		if(fNum >= 1){
			if(eojeolArray[fNum-1].getMorphemes()[0] != null){
				mKey = 1;
				morphM1 = eojeolArray[fNum-1].getMorphemes();
				tagM1 = eojeolArray[fNum-1].getTags();
			}
		}
		/**
		 * feature의 위치가 -2이하인 경우 fNum-2위치의 어절이 존재하는지 확인한다. 또한 fNum-2위치의 어절이 null인지 확인 
		 * fNum-2위치의 어절과 태그를 변수에 저장한다.
		 */
		if(fNum >= 2  ){
			if(eojeolArray[fNum-2].getMorphemes()[0] != null){
				mKey = 2;
				morphM2 = eojeolArray[fNum-2].getMorphemes();
				tagM2 = eojeolArray[fNum-2].getTags();
			}
		}
		/**
		 * fNum+1위치의 어절이 null인지 확인 
		 * fNum+1위치의 어절과 태그를 변수에 저장한다.
		 */
		if(eojeolArray[fNum+1].getMorphemes()[0] != null){
			pKey = 1;
			morphP1 = eojeolArray[fNum+1].getMorphemes();
			tagP1 = eojeolArray[fNum+1].getTags();
		}
		
		if(morphP1[0] != null){
			if(eojeolArray[fNum+2].getMorphemes()[0] != null)
			{
				pKey = 2;
				morphP2 = eojeolArray[fNum+2].getMorphemes();
				tagP2 = eojeolArray[fNum+2].getTags();
			}
		}
			if(pKey >= 1){
				if(tagP1[0].charAt(0) == 'P'){ //tagP1[j].charAt(0) == 'P' //tagP1[j].equals("PA")||tagP1[j].equals("PV"
					patternNum = NV_PATTERN;
					sentAnalyze();
					check = 1;
				}else if(pKey >= 2){
					if(tagP1[0].equals("MA") && tagP2[0].charAt(0)=='P'){
						patternNum = NZV_PATTERN;
						sentAnalyze();
						check = 1;
						}
					else if(tagP1[0].equals("MA") && tagP2[0].charAt(0)=='N'){
						patternNum = NZN_PATTERN;
						sentAnalyze();
						check = 1;
						}
					else if(tagP1[0].charAt(0)=='N' && tagP2[0].charAt(0)=='N'){
						patternNum = NNN_PATTERN;
						sentAnalyze();
						check = 1;
						}
				}
			}
			
			if(mKey >= 1 && check != 1){// mKey가  1이면 feature의 배열 위치가 -1일때
				if(mKey != 2){// mKey가  2이면 feature의 배열 위치가 -2이상 일때
					if(tagM1[0].charAt(0)=='P'){
						patternNum = VN_PATTERN;
						sentAnalyze();
						check = 1;
						}
					else if(tagM1[0].charAt(0)=='N'){
						patternNum = NN_PATTERN;
						sentAnalyze();
						check = 1;
						}
				}
				else if(tagM1[0].charAt(0)=='P' && !tagM2[0].equals("MA")){
					patternNum = VN_PATTERN;
					sentAnalyze();
					check = 1;
				}
				else if(tagM1[0].charAt(0)=='N' && !tagM2[0].equals("MA")){
					patternNum = NN_PATTERN;
					sentAnalyze();
					check = 1;
					}
				else if(tagM2[0].equals("MA") && tagM1[0].charAt(0)=='P'){
					patternNum = ZVN_PATTERN;
					sentAnalyze();
					check = 1;
					}
				else if(tagM2[0].equals("MA") && tagM1[0].charAt(0)=='N'){
					patternNum = ZNN_PATTERN;
					sentAnalyze();
					check = 1;
					}
				}
			return se;
		}
		
	
	public void sentAnalyze(){

		if(patternNum == NV_PATTERN){
			tempMorph = DictionaryReader.VACor.get(morphP1[0]);
			if(tempMorph != null){
				morphP1[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphP1[0]);
				sentValue = Integer.parseInt(sentMorph);
			}
			start = fNum;
			end = fNum + 2;
		}
		else if(patternNum == NZV_PATTERN){
			tempMorph = DictionaryReader.VACor.get(morphP2[0]);
			if(tempMorph != null){
				morphP2[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphP2[0]);
				sentValue = Integer.parseInt(sentMorph);
			}
			start = fNum;
			end = fNum + 3;
		}
		//sentMorph가 널인 경우 에러 발생
		else if(patternNum == NZN_PATTERN){
			/*tempMorph = DictionaryReader.VACor.get(morphP2[0]);
			if(tempMorph != null){
				morphP2[0] = tempMorph;*/
				sentMorph = DictionaryReader.sentimentDic.get(morphP2[0]);
				if(sentMorph != null)
				sentValue = Integer.parseInt(sentMorph);
			//}
			start = fNum;
			end = fNum + 3;
		}
		else if(patternNum == NNN_PATTERN){
			/*tempMorph = DictionaryReader.VACor.get(morphP2[0]);
			if(tempMorph != null){
				morphP2[0] = tempMorph;*/
				sentMorph = DictionaryReader.sentimentDic.get(morphP2[0]);
				if(sentMorph != null)
				sentValue = Integer.parseInt(sentMorph);
			//}
			start = fNum;
			end = fNum + 3;
		}
		else if(patternNum == VN_PATTERN){
			tempMorph = DictionaryReader.VACor.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphM1[0]);
				sentValue = Integer.parseInt(sentMorph);
			}
			start = fNum - 1;
			end = fNum + 1;
		}	
		else if(patternNum == NN_PATTERN){
			/*tempMorph = DictionaryReader.VACor.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;*/
				sentMorph = DictionaryReader.sentimentDic.get(morphM1[0]);
				if(sentMorph != null)
				sentValue = Integer.parseInt(sentMorph);
			//}
			start = fNum - 1;
			end = fNum + 1;
		}	
		else if(patternNum == ZVN_PATTERN){
			tempMorph = DictionaryReader.VACor.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphM1[0]);
				sentValue = Integer.parseInt(sentMorph);
			}
			start = fNum - 2;
			end = fNum + 1;
		}	
		else if(patternNum == ZNN_PATTERN){
			/*tempMorph = DictionaryReader.VACor.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;*/
				sentMorph = DictionaryReader.sentimentDic.get(morphM1[0]);
				if(sentMorph != null)
				sentValue = Integer.parseInt(sentMorph);
			//}
			start = fNum - 2;
			end = fNum + 1;
		}	
		
		print();
	}
	public void print(){
		
		newArray = new Eojeol[end - start + 1];
		for(int i = 0 ; i < end - start + 1 ; i++){
			newArray[i] = new Eojeol(null,null);
		}
		
		System.out.print("{ ");
		
		for (int k = start; k < end; k++) { //k는 문장의 속성 위치
			
			//어절 배열에 있는 단어를 하나 씩 출력
			morphemes = eojeolArray[k].getMorphemes();
			tags = eojeolArray[k].getTags();
			morphList.clear();
			tagList.clear();
			morphList.add(morphemes[0]);
			tagList.add(tags[0]);
				for (int l = 0; l < morphemes.length; l++) {
					System.out.print(morphemes[l]);
					System.out.print("/");
					System.out.print(tags[l]);
				}
				System.out.print(", ");
				
				
				
				newArray[k-start].setMorphemes(morphList.toArray(new String[0]));
				newArray[k-start].setTags(tagList.toArray(new String[0]));
		}
		
		se = new SentimentEojeol(newArray, sentValue);
		se.length = end - start;
		
		System.out.print("} ");
		System.out.print("-> ");
		
		if(sentValue == 1){
			System.out.print("긍정 ");
		}
		else if(sentValue == 0){
			System.out.print("중립 ");
		}
		else if(sentValue == -1){
			System.out.print("부정 ");
		}
		
		if(patternNum ==1){
			System.out.print("(NV)");
		}else if(patternNum ==2){
			System.out.print("(NZV)");
		}else if(patternNum ==3){
			System.out.print("(NZN)");
		}else if(patternNum ==4){
			System.out.print("(NNN)");
		}else if(patternNum ==5){
			System.out.print("(VN)");
		}else if(patternNum ==6){
			System.out.print("(NN)");
		}else if(patternNum ==7){
			System.out.print("(ZVN)");
		}else if(patternNum ==8){
			System.out.print("(ZNN)");
		}
		System.out.print(" ");
		//return sentEojeols;
	}
}