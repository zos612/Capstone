package algorithms;

import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;

public class SentimentAnalyzer {
	public final static int MAX_EOJEOLS = 10000;
	
	public SentimentEojeol se = null;
	
	private LinkedList<String> morphList = null;
	
	private LinkedList<String> tagList = null;
	
	private String[] morphM1 = new String[1];
	private String[] tagM1 = new String[1];
	private String[] morphM2 = new String[1];
	private String[] tagM2 = new String[1];
	private String[] morphP1 = new String[1];
	private String[] tagP1 = new String[1];
	private String[] morphP2 = new String[1];
	private String[] tagP2 = new String[1];
	private String[] morphP1Tmp = new String[1];
	private String[] morphP2Tmp = new String[1];
	
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

	
	public int sentimentV = 0; //V : 동사 감정수치
	public int sentimentZ; //Z : 부사 감정수치
	public int sentiment;
	public int arrayNum = 0;
	public String sentMorph = null;
	
	String[] morphemes = null;
	String[] tags = null;
	
	Eojeol[] eojeolArray;
	Eojeol[] newArray;
	
	int fNum = 0;
	int fKey = 0;
	int start;
	int end;
	String tempMorph = null;
	
	public SentimentAnalyzer(){
		morphList = new LinkedList<String>();
		tagList = new LinkedList<String>();
		
	}
	
	public SentimentEojeol patternAnalyze(Eojeol[] eojeolArray, int fNum){
		//String []startTag = eojeolArray[0].getTags();
		//절대 위치라 불안전함. 이 부분에서 에러 날 가능성 높음
		//if(startTag[0].charAt(0)=='P'){
		this.eojeolArray = eojeolArray;
		this.fNum = fNum;
		fKey = 0;
		
		if(fNum >= 1){
			fKey = 1;
			morphM1 = eojeolArray[fNum-1].getMorphemes();
			tagM1 = eojeolArray[fNum-1].getTags();
		}
		if(fNum >= 2  ){
			if(eojeolArray[fNum-2].getMorphemes()[0] != null){
			fKey = 2;
			morphM2 = eojeolArray[fNum-2].getMorphemes();
			tagM2 = eojeolArray[fNum-2].getTags();
			}
		}
		/*morphP1Tmp = eojeolArray[fNum+1].getMorphemes();
		if(morphP1Tmp[0] != null){*/
		morphP1 = eojeolArray[fNum+1].getMorphemes();
		tagP1 = eojeolArray[fNum+1].getTags();
		
		if(morphP1[0] != null){
			/*
		morphP2Tmp = eojeolArray[fNum+2].getMorphemes();
		if(morphP2Tmp[0] != null){*/
			morphP2 = eojeolArray[fNum+2].getMorphemes();
			tagP2 = eojeolArray[fNum+2].getTags();
		}
		for (int j = 0; j < morphP1.length; j++){
			if(tagP1[j].charAt(0) == 'P'){ //tagP1[j].charAt(0) == 'P' //tagP1[j].equals("PA")||tagP1[j].equals("PV"
				patternNum = NV_PATTERN;
				sentAnalyze();
			}
			else if(tagP1[j].equals("MA") && (tagP2[j].charAt(0)=='P')){
				patternNum = NZV_PATTERN;
				sentAnalyze();
				}
			else if(tagP1[j].equals("MA") && tagP2[j].charAt(0)=='N'){
				patternNum = NZN_PATTERN;
				sentAnalyze();
				}
			else if(tagP1[j].charAt(0)=='N' && tagP2[j].charAt(0)=='N'){
				patternNum = NNN_PATTERN;
				sentAnalyze();
				}
			else if(fKey >= 1){// fKey가  1이면 feature의 배열 위치가 1일때
				if(fKey == 2){// fKey가  2이면 feature의 배열 위치가 2이상 일때
					if(tagM1[j].charAt(0)=='P' && !tagM2[j].equals("MA")){
						patternNum = VN_PATTERN;
						sentAnalyze();
					}
					else if(tagM1[j].charAt(0)=='N' && !tagM2[j].equals("MA")){
						patternNum = NN_PATTERN;
						sentAnalyze();
						}
					else if(tagM2[j].equals("MA") && tagM1[j].charAt(0)=='P'){
						patternNum = ZVN_PATTERN;
						sentAnalyze();
						}
					else if(tagM2[j].equals("MA") && tagM1[j].charAt(0)=='N'){
						patternNum = ZNN_PATTERN;
						sentAnalyze();
						}
				}
				else if(tagM1[j].charAt(0)=='P'){
					patternNum = VN_PATTERN;
					sentAnalyze();
					}
				else if(tagM1[j].charAt(0)=='N'){
					patternNum = NN_PATTERN;
					sentAnalyze();
					}
				}
				else{
					return null;
				}
			}
		return se;
		}
		
	
	public void sentAnalyze(){

		if(patternNum == NV_PATTERN){
			tempMorph = DictionaryReader.correctionDic.get(morphP1[0]);
			if(tempMorph != null){
				morphP1[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphP1[0]);
				sentimentV = Integer.parseInt(sentMorph);
			}
			start = fNum;
			end = fNum + 2;
		}
		else if(patternNum == NZV_PATTERN){
			tempMorph = DictionaryReader.correctionDic.get(morphP2[0]);
			if(tempMorph != null){
				morphP2[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphP2[0]);
				sentimentV = Integer.parseInt(sentMorph);
			}
			start = fNum;
			end = fNum + 3;
		}
		//sentMorph가 널인 경우 에러 발생
		else if(patternNum == NZN_PATTERN){
			tempMorph = DictionaryReader.nounDic.get(morphP2[0]);
			if(tempMorph != null){
				morphP2[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphP2[0]);
				sentimentV = Integer.parseInt(sentMorph);
			}
			start = fNum;
			end = fNum + 3;
		}
		else if(patternNum == NNN_PATTERN){
			tempMorph = DictionaryReader.nounDic.get(morphP2[0]);
			if(tempMorph != null){
				morphP2[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphP2[0]);
				sentimentV = Integer.parseInt(sentMorph);
			}
			start = fNum;
			end = fNum + 3;
		}
		else if(patternNum == VN_PATTERN){
			tempMorph = DictionaryReader.correctionDic.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphM1[0]);
				sentimentV = Integer.parseInt(sentMorph);
			}
			start = fNum - 1;
			end = fNum + 1;
		}	
		else if(patternNum == NN_PATTERN){
			tempMorph = DictionaryReader.nounDic.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphM1[0]);
				sentimentV = Integer.parseInt(sentMorph);
			}
			start = fNum - 1;
			end = fNum + 1;
		}	
		else if(patternNum == ZVN_PATTERN){
			tempMorph = DictionaryReader.correctionDic.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphM1[0]);
				sentimentV = Integer.parseInt(sentMorph);
			}
			start = fNum - 2;
			end = fNum + 1;
		}	
		else if(patternNum == ZNN_PATTERN){
			tempMorph = DictionaryReader.nounDic.get(morphM1[0]);
			if(tempMorph != null){
				morphM1[0] = tempMorph;
				sentMorph = DictionaryReader.sentimentDic.get(morphM1[0]);
				sentimentV = Integer.parseInt(sentMorph);
			}
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
		
		se = new SentimentEojeol(newArray, sentimentV);
		se.length = end - start;
		
		System.out.print("} ");
		System.out.print("-> ");
		
		if(sentimentV == 1){
			System.out.print("긍정 ");
		}
		else if(sentimentV == 0){
			System.out.print("중립 ");
		}
		else if(sentimentV == -1){
			System.out.print("부정 ");
		}
		//return sentEojeols;
	}
}