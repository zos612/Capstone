package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import algorithms.DictionaryReader;
import algorithms.SentimentEojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;

public class SentimentAnalyzer3 {
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
	private String tagM1;
	private String[] morphM2 = new String[1];
	private String tagM2;
	private String[] morphP1 = new String[1];
	private String tagP1;
	private String[] morphP2 = new String[1];
	private String tagP2;

	
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
	Eojeol[] seMorphArray; //se에 넣을 어절배열
	
	String[] morpheme = null; //어절
	String[] tag = null; //태그
	
	public String strSentValue = null; //감정수치 String값
	
	public int arrayNum = 0;
	
	Eojeol[] morphArray;
	Eojeol[] morphArrayCP;
	

	
	int fIndex = 0; // feature의 배열 위치
	int mKey = 0; // feature과 minus거리에 따른 키
	int pKey = 0; // feature과 plus거리에 따른 키
	int check = 0; // 감정 분석이 두 번 이상 되지 않도록 체크함. 값이'0'이면 감정분석 x 1이면 감정분석 o
	int start;
	int end;
	String tempMorph = null;
	
	private Eojeol[] tmpEojeol = null;
	
	public int seNum = 0;
	
	public ArrayList<SentimentEojeol> seArray = null;
	
	public int eojeolArraySize = 0;
	
	public SentimentAnalyzer3(){
		morphList = new LinkedList<String>();
		tagList = new LinkedList<String>();
		seArray = new ArrayList<SentimentEojeol>();
	}
	
	public ArrayList<SentimentEojeol> patternAnalyze(Eojeol[] morphArray, int fIndex){
		check = 0;
		mKey = 0;
		pKey = 0;
		seValue = 0;
		seFeature = null;
		seSentWord = null;
		se = null;
		end = 0;
		start = 0;
		this.morphArray = morphArray;
		this.fIndex = fIndex;
		int arrayEnd = morphArray.length - 1;
		seFeature = morphArray[fIndex].getMorphemes()[0];
		
		
		//문장의 마지막에서 .으로 끝나지 않는경우 마지막 배열이 null로 끝나지 않아서 값을 불러올 떄 null포인터 에러가 뜬다
				//이를 막기위해 마지막 배열이 null이 아니면 배열의 크기를 1늘려 null인 마지막 배열을 생성한다. 
				if(morphArray[arrayEnd].getMorphemes()[0] != null){
					morphArray = Arrays.copyOf(morphArray, morphArray.length + 1);
					morphArray[arrayEnd + 1] = new Eojeol(new String[1], null);
					this.morphArray = morphArray;
				}
				
				morphArrayCP = morphArray.clone();
		/*morphM1[0] = null;
		morphM2[0] = null;
		morphP1[0] = null;
		morphP2[0] = null;*/
		/*tagM1 = null;
		tagM2 = null;
		tagP1 = null;
		tagP2 = null;*/
		
		
		/**
		 * fNum은 feature의 배열 상의 위치이다.
		 * feature의 위치가 -1이하인 경우 fIndex-1위치의 어절이 존재하는지 확인한다. 또한 fIndex-1위치의 어절이 null인지 확인 
		 * fIndex-1위치의 어절과 태그를 변수에 저장한다.
		 */
		if(fIndex >= 1){
			if(morphArray[fIndex-1].getMorphemes()[0] != null){
				mKey = 1;
				morphM1 = morphArrayCP[fIndex-1].getMorphemes();
				tagM1 =  morphArrayCP[fIndex-1].getTag(0);
			}
		}
		/**
		 * feature의 위치가 -2이하인 경우 fIndex-2위치의 어절이 존재하는지 확인한다. 또한 fIndex-2위치의 어절이 null인지 확인 
		 * fIndex-2위치의 어절과 태그를 변수에 저장한다.
		 */
		if(fIndex >= 2 && check != 1){
			if(morphArrayCP[fIndex-2].getMorphemes()[0] != null){
				mKey = 2;
				morphM2 = morphArrayCP[fIndex-2].getMorphemes();
				tagM2 = new String(morphArrayCP[fIndex-2].getTag(0));
			}
		}
		/**
		 * fIndex+1위치의 어절이 null인지 확인 
		 * fIndex+1위치의 어절과 태그를 변수에 저장한다.
		 */
		if(morphArrayCP[fIndex+1].getMorphemes()[0] != null){
			pKey = 1;
			morphP1 = morphArrayCP[fIndex+1].getMorphemes();
			tagP1 = new String(morphArrayCP[fIndex+1].getTag(0));
		}
		
		if(morphArrayCP[fIndex+1].getMorphemes()[0] != null){
			if(morphArrayCP[fIndex+2].getMorphemes()[0] != null)
			{
				pKey = 2;
				morphP2 = morphArrayCP[fIndex+2].getMorphemes();
				tagP2 = new String(morphArrayCP[fIndex+2].getTag(0));
			}
		}
			if(pKey >= 1 && check != 1){
				//배열의  fIndex가 0인 경우 tagM1을 참조하면 널 포인터 에러가 나기 때문에 조건을 붙임
				if(tagP1.charAt(0) == 'P'){
					if(tagP2 != null && tagP2.charAt(0) == 'P'){
							patternNum = NVV_PATTERN;
							sentAnalyze();
							check = 1;
					}else if(tagM1 != null && tagM1.equals("MA")){
							patternNum = ZNV_PATTERN;
							sentAnalyze();
							check = 1;
							//예쁜 디자인이 좋다
					}else if(tagM1 != null && tagM1.charAt(0) == 'P'){
							patternNum = VNV_PATTERN;
							sentAnalyze();
							check = 1;
					}else{
						patternNum = NV_PATTERN;
						sentAnalyze();
						check = 1;
						
					} 
				}//0 디자인 예쁘다 0
						/*else if(tagM1 == null || tagP2 == null){
					}
						patternNum = NV_PATTERN;
						sentAnalyze();
						check = 1;
					}*/
				}
				
				if(pKey >= 2 && check != 1){
					if(tagP1.equals("MA") && tagP2.charAt(0)=='P'){
						patternNum = NZV_PATTERN;
						sentAnalyze();
						check = 1;
						}
					else if(tagP1.equals("MA") && tagP2.charAt(0)=='N'){
						patternNum = NZN_PATTERN;
						sentAnalyze();
						check = 1;
						}
					else if(tagP1.charAt(0)=='N' && tagP2.charAt(0)=='N'){
						patternNum = NNN_PATTERN;
						sentAnalyze();
						check = 1;
						}
					else if(tagP1.charAt(0)=='N' && tagP2.charAt(0)=='P'){
						patternNum = NNV_PATTERN;
						sentAnalyze();
						check = 1;
						}
					/*else if(tagP1.charAt(0)=='P' && tagP2.charAt(0)=='P'){
						patternNum = NVV_PATTERN;
						sentAnalyze();
						check = 1;
						}*/
				}
	//}
			
			if(mKey >= 1 && check != 1){// mKey가  1이면 feature의 배열 위치가 1 일때
				/*if(mKey != 2){// mKey가  2이면 feature의 배열 위치가 1 일때
					if(tagM1.charAt(0)=='P'){
						patternNum = VN_PATTERN;
						sentAnalyze();
						check = 1;
					}
					else if(tagM1.charAt(0)=='N'){
						patternNum = NN_PATTERN;
						sentAnalyze();
						check = 1;
						}
				}*/
				if(tagM1.charAt(0) == 'P'){
					if(tagP1 !=null && tagP1.charAt(0)=='N'){
					patternNum = VNN_PATTERN;
					sentAnalyze();
					check = 1;
					}else if(tagM2 != null && tagM2.equals("MA")){
						patternNum = ZVN_PATTERN;
						sentAnalyze();
						check = 1;
					}else {
					patternNum = VN_PATTERN;
					sentAnalyze();
					check = 1;
					}
				}else if(tagM1.charAt(0)=='N'){
					if(tagM2 != null && tagM2.equals("MA")){
						patternNum = ZNN_PATTERN;
						sentAnalyze();
						check = 1;
						}
					else{
						patternNum = NN_PATTERN;
						sentAnalyze();
						check = 1;
					}
				}
				/*if(mKey >= 1 && pKey >= 1 && check != 1){
					if(tagM1.equals("MA") && tagP1.charAt(0)=='P'){
						patternNum = ZNV_PATTERN;
						sentAnalyze();
						check = 1;
					}if(tagM1.charAt(0)=='P' && tagP1.charAt(0)=='P'){
						patternNum = VNV_PATTERN;
						sentAnalyze();
						check = 1;
					}
				}*/
				
				}
			if(check == 1)
				print();
			
			return seArray;
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
		else if(patternNum == ZNV_PATTERN){
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
			start = fIndex - 1;
			end = fIndex + 2;
		}	
		else if(patternNum == NNV_PATTERN){
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
		else if(patternNum == NVV_PATTERN){
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
		else if(patternNum == VNV_PATTERN){
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
			start = fIndex - 1;
			end = fIndex + 2;
		}	
		else if(patternNum == VNN_PATTERN){
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
			end = fIndex + 2;
		}
		//print();
		
	}
	public void print(){
		
		seMorphArray = new Eojeol[end - start + 1];
		for(int i = 0 ; i < end - start + 1 ; i++){
			seMorphArray[i] = new Eojeol(null,null);
		}
		
		System.out.print("{ ");
		
		for (int k = start; k < end; k++) { //k는 문장의 속성 위치
			
			//어절 배열에 있는 단어를 하나 씩 출력
			morpheme = morphArray[k].getMorphemes();
			tag = morphArray[k].getTags();
			morphList.clear();
			tagList.clear();
			morphList.add(morpheme[0]);
			tagList.add(tag[0]);
			
					System.out.print(morpheme[0]);
					System.out.print("/");
					System.out.print(tag[0]);
				
				System.out.print(", ");
				
				seMorphArray[k-start].setMorphemes(morphList.toArray(new String[0]));
				seMorphArray[k-start].setTags(tagList.toArray(new String[0]));
		}

			
		se = new SentimentEojeol(seFeature, seSentWord, seValue, seMorphArray);
		se.length = end - start;
		
		
		
		//감정단어가 비어있으면 의미가 없으므로 출력하지 않는다.
		if(seSentWord != null){
			//seArray배열에 어절과 감정수치 저장
			seArray.add(se);
		}
			
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
			System.out.print("(NV_1)");
		}else if(patternNum ==2){
			System.out.print("(NZV_2)");
		}else if(patternNum ==3){
			System.out.print("(NZN_3)");
		}else if(patternNum ==4){
			System.out.print("(NNN_4)");
		}else if(patternNum ==5){
			System.out.print("(VN_5)");
		}else if(patternNum ==6){
			System.out.print("(NN_6)");
		}else if(patternNum ==7){
			System.out.print("(ZVN_7)");
		}else if(patternNum ==8){
			System.out.print("(ZNN_8)");
		}else if(patternNum ==9){
			System.out.print("(ZV_9)");
		}else if(patternNum ==10){
			System.out.print("(ZNV_10)");
		}else if(patternNum ==11){
			System.out.print("(NNV_11)");
		}else if(patternNum ==12){
			System.out.print("(NVV_12)");
		}else if(patternNum ==13){
			System.out.print("(VNV_13)");
		}else if(patternNum ==14){
			System.out.print("(VNN_14)");
		}
		
		/*public final static int NV_PATTERN = 1;
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
		public final static int VNN_PATTERN = 14;*/
		
		System.out.print(" ");
		//return sentEojeols;
		}
	}

