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
	private String[] morphM1 = new String[0];
	private String[] tagM1 = new String[0];
	private String[] morphM2 = new String[0];
	private String[] tagM2 = new String[0];
	private String[] morphP1 = new String[0];
	private String[] tagP1 = new String[0];
	private String[] morphP2 = new String[0];
	private String[] tagP2 = new String[0];

	
	//private int arrayNum = 0;
	public final static int NV_PATTERN = 1;
	public final static int NZV_PATTERN = 2;
	public final static int NZN_PATTERN = 3;
	public final static int NNN_PATTERN = 4;
	public final static int VN_PATTERN = 5;
	public final static int NN_PATTERN = 6;
	public final static int ZVN_PATTERN = 7;
	public final static int ZNN_PATTERN = 8;
	public final static int ZV_PATTERN = 9;
	public final static int ZNV_PATTERN = 10;
	public final static int NNV_PATTERN = 11;
	public final static int NVV_PATTERN = 12;
	public final static int VNV_PATTERN = 13;
	public final static int VNN_PATTERN = 14;





	public int patternNum = 0;
	
	//se객체에 넣을 데이터
	public int seValue = 0; //se에 넣을 감정수치
	public String seSentWord = null; //se에 넣을 감정단어
	public String seFeature = null; //se에 넣을 특징
	Eojeol[] seArray; //se에 넣을 어절배열
	
	String[] morpheme = null; //어절
	String[] tag = null; //태그
	
	public String strSentValue = null; //감정수치 String값
	
	public int arrayNum = 0;
	
	Eojeol[] eojeolArray;

	
	int fIndex = 0; // feature의 배열 위치
	int mKey = 0; // feature과 minus거리에 따른 키
	int pKey = 0; // feature과 plus거리에 따른 키
	int check = 0; // 감정 분석이 두 번 이상 되지 않도록 체크함. 값이'0'이면 감정분석 x 1이면 감정분석 o
	int start;
	int end;
	String tempMorph = null;
	
	public SentimentAnalyzer(){
		morphList = new LinkedList<String>();
		tagList = new LinkedList<String>();
	}
	
	public SentimentEojeol patternAnalyze(Eojeol[] eojeolArray, int fIndex){
		check = 0;
		mKey = 0;
		pKey = 0;
		seValue = 0;
		seFeature = null;
		seSentWord = null;
		se = null;
		end = 0;
		start = 0;
		this.eojeolArray = eojeolArray;
		this.fIndex = fIndex;
		int arrayEnd = eojeolArray.length - 1;
		seFeature = eojeolArray[fIndex].getMorphemes()[0];
		
		//문장의 마지막에서 .으로 끝나지 않는경우 마지막 배열이 null로 끝나지 않아서 값을 불러올 떄 null포인터 에러가 뜬다
				//이를 막기위해 마지막 배열이 null이 아니면 배열의 크기를 1늘려 null인 마지막 배열을 생성한다. 
				if(eojeolArray[arrayEnd].getMorphemes()[0] != null){
					eojeolArray = Arrays.copyOf(eojeolArray, eojeolArray.length + 1);
					eojeolArray[arrayEnd + 1] = new Eojeol(new String[1],null);
					this.eojeolArray = eojeolArray;
				}
		/*morphM1 = null;
		morphM2 = null;
		morphP1 = null;
		morphP2 = null;
		tagM1 = null;
		tagM2 = null;
		tagP1 = null;
		tagP2 = null;*/
		
		
		/**
		 * fNum은 feature의 배열 상의 위치이다.
		 * feature의 위치가 -1이하인 경우 fIndex-1위치의 어절이 존재하는지 확인한다. 또한 fIndex-1위치의 어절이 null인지 확인 
		 * fIndex-1위치의 어절과 태그를 변수에 저장한다.
		 */
		if(fIndex >= 1){
			if(eojeolArray[fIndex-1].getMorphemes()[0] != null){
				mKey = 1;
				morphM1 = eojeolArray[fIndex-1].getMorphemes();
				tagM1 = eojeolArray[fIndex-1].getTags();
			}
		}
		/**
		 * feature의 위치가 -2이하인 경우 fIndex-2위치의 어절이 존재하는지 확인한다. 또한 fIndex-2위치의 어절이 null인지 확인 
		 * fIndex-2위치의 어절과 태그를 변수에 저장한다.
		 */
		if(fIndex >= 2  ){
			if(eojeolArray[fIndex-2].getMorphemes()[0] != null){
				mKey = 2;
				morphM2 = eojeolArray[fIndex-2].getMorphemes();
				tagM2 = eojeolArray[fIndex-2].getTags();
			}
		}
		/**
		 * fIndex+1위치의 어절이 null인지 확인 
		 * fIndex+1위치의 어절과 태그를 변수에 저장한다.
		 */
		if(eojeolArray[fIndex+1].getMorphemes()[0] != null){
			pKey = 1;
			morphP1 = eojeolArray[fIndex+1].getMorphemes();
			tagP1 = eojeolArray[fIndex+1].getTags();
		}
		
		if(eojeolArray[fIndex+1].getMorphemes()[0] != null){
			if(eojeolArray[fIndex+2].getMorphemes()[0] != null)
			{
				pKey = 2;
				morphP2 = eojeolArray[fIndex+2].getMorphemes();
				tagP2 = eojeolArray[fIndex+2].getTags();
			}
		}
			if(pKey >= 1){
				if(tagP1[0].charAt(0) == 'P'){ //tagP1[0][0][j].charAt(0) == 'P' //tagP1[0][j].equals("PA")||tagP1[0][j].equals("PV"
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
			
			if(mKey >= 1 && check != 1){// mKey가  1이면 feature의 배열 위치가 -1이하 일때
				if(mKey != 2){// mKey가  2이면 feature의 배열 위치가 -2이하 일때
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
				}else if(tagM1[0].charAt(0)=='N' && !tagM2[0].equals("MA")){
					patternNum = NN_PATTERN;
					sentAnalyze();
					check = 1;
						
				}else if(tagM1[0].charAt(0)=='P' && !tagM2[0].equals("MA")){
					patternNum = VN_PATTERN;
					sentAnalyze();
					check = 1;
				
				}else if(tagM1[0].charAt(0)=='N' && !tagM2[0].equals("MA")){
					patternNum = NN_PATTERN;
					sentAnalyze();
					check = 1;
					
				}else if(tagM2[0].equals("MA") && tagM1[0].charAt(0)=='P'){
					patternNum = ZVN_PATTERN;
					sentAnalyze();
					check = 1;
				}else if(tagM2[0].equals("MA") && tagM1[0].charAt(0)=='N'){
					patternNum = ZNN_PATTERN;
					sentAnalyze();
					check = 1;
					}
				
				}
			
			
			if(check == 1)
			print();
			//감정단어가 비어있으면 의미가 없으므로 출력하지 않는다.
			if(seSentWord == null)
				return null;
			
			return se;
		}
		
	
	public void sentAnalyze(){

		if(patternNum == NV_PATTERN){
			//형용사 또는 동사인 감정단어가 보정사전에 등독되 있지 않으면  단어보정과 감정수치를 구하지 않는다 
			tempMorph = DictionaryReader.VACor.get(morphP1[0]);

			if(tempMorph != null){
				morphP1[0] = tempMorph;
				strSentValue = DictionaryReader.sentimentDic.get(morphP1[0]);
				if(strSentValue != null){
				seValue = Integer.parseInt(strSentValue);
				seSentWord = morphP1[0];
				}
			}
			start = fIndex;
			end = fIndex + 2;
		}
		else if(patternNum == NZV_PATTERN){
			//형용사 또는 동사인 감정단어가 보정사전에 등독되 있지 않으면  단어보정과 감정수치를 구하지 않는다 
			tempMorph = DictionaryReader.VACor.get(morphP2[0]);
			if(tempMorph != null){
				morphP2[0] = tempMorph;
				strSentValue = DictionaryReader.sentimentDic.get(morphP2[0]);
				if(strSentValue != null){
					seValue = Integer.parseInt(strSentValue);
					seSentWord = morphP2[0];
				}
			}

			start = fIndex;
			end = fIndex + 3;
		}
		else if(patternNum == NZN_PATTERN){
				//명사인 감정단어가 감정수치 사전에 등록되 있으면 감정수치를 저장한다.
				strSentValue = DictionaryReader.sentimentDic.get(morphP2[0]);
				if(strSentValue != null){
				seValue = Integer.parseInt(strSentValue);
				seSentWord = morphP2[0];
				}
				//명사인 감정단어가 보정사전에 등록되있으면  보정단어를 저장한다.
				tempMorph = DictionaryReader.nounCor.get(morphP2[0]);
				if(tempMorph != null){
					morphP2[0] = tempMorph;
				}
			start = fIndex;
			end = fIndex + 3;
		}
		else if(patternNum == NNN_PATTERN){
			//명사인 감정단어가 감정수치 사전에 등록되 있으면 감정수치를 저장한다.
				strSentValue = DictionaryReader.sentimentDic.get(morphP2[0]);
			if(strSentValue != null){
				seValue = Integer.parseInt(strSentValue);
				seSentWord = morphP2[0];
			}
			//명사인 감정단어가 보정사전에 등록되있으면  보정단어를 저장한다.
				tempMorph = DictionaryReader.nounCor.get(morphP2[0]);
			if(tempMorph != null){
				morphP2[0] = tempMorph;
			}
			start = fIndex;
			end = fIndex + 3;
		}
		else if(patternNum == VN_PATTERN){
			tempMorph = DictionaryReader.VACor.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;
				strSentValue = DictionaryReader.sentimentDic.get(morphM1[0]);
				if(strSentValue != null){
				seValue = Integer.parseInt(strSentValue);
				seSentWord = morphM1[0];
				}
			}
			start = fIndex - 1;
			end = fIndex + 1;
		}	
		else if(patternNum == NN_PATTERN){
			/*tempMorph = DictionaryReader.VACor.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;*/
				strSentValue = DictionaryReader.sentimentDic.get(morphM1[0]);
				if(strSentValue != null){
				seValue = Integer.parseInt(strSentValue);
				seSentWord = morphM1[0];
				}
			//}

			start = fIndex - 1;
			end = fIndex + 1;
		}	
		else if(patternNum == ZVN_PATTERN){
			tempMorph = DictionaryReader.VACor.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;
				strSentValue = DictionaryReader.sentimentDic.get(morphM1[0]);
				if(strSentValue != null){
				seValue = Integer.parseInt(strSentValue);
				seSentWord = morphM1[0];
				}
			}
			start = fIndex - 2;
			end = fIndex + 1;
		}	
		else if(patternNum == ZNN_PATTERN){
			/*tempMorph = DictionaryReader.VACor.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;*/
				strSentValue = DictionaryReader.sentimentDic.get(morphM1[0]);
				if(strSentValue != null){
				seValue = Integer.parseInt(strSentValue);
				seSentWord = morphM1[0];
				}
			//}
			start = fIndex - 2;
			end = fIndex + 1;
		}	
		
	}
	public void print(){
		
		seArray = new Eojeol[end - start + 1];
		for(int i = 0 ; i < end - start + 1 ; i++){
			seArray[i] = new Eojeol(null,null);
		}
		
		System.out.print("{ ");
		
		for (int k = start; k < end; k++) { //k는 문장의 속성 위치
			
			//어절 배열에 있는 단어를 하나 씩 출력
			morpheme = eojeolArray[k].getMorphemes();
			tag = eojeolArray[k].getTags();
			morphList.clear();
			tagList.clear();
			morphList.add(morpheme[0]);
			tagList.add(tag[0]);
			
					System.out.print(morpheme[0]);
					System.out.print("/");
					System.out.print(tag[0]);
				
				System.out.print(", ");
				
				seArray[k-start].setMorphemes(morphList.toArray(new String[0]));
				seArray[k-start].setTags(tagList.toArray(new String[0]));
		}
		
		se = new SentimentEojeol(seFeature, seSentWord, seValue, seArray);
		se.length = end - start;
		
		System.out.print("} ");
		System.out.print("-> ");
		
		if(seValue == 1){
			System.out.print("긍정 ");
		}
		else if(seValue == 2){
			System.out.print("중립 ");
		}
		else if(seValue == -1){
			System.out.print("부정 ");
		}
		else if(seValue == 0){
			System.out.print("없음 ");
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