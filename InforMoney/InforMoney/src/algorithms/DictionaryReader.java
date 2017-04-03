package algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DictionaryReader {
	
	public static Dictionary sentimentDic = null;
	
	public static Dictionary correctionDic = null;
	
	public static Dictionary nounDic = null;
	
	public DictionaryReader(){
		sentimentDic = new Dictionary();
		correctionDic = new Dictionary();
		nounDic = new Dictionary();
		
	}
	public void init() throws UnsupportedEncodingException, FileNotFoundException, IOException{
		sentimentDic.readDic(".\\data\\kE\\dic_sentiment.txt");
		correctionDic.readDic(".\\data\\kE\\dic_verb_adjective_correction.txt");
		nounDic.readDic(".\\data\\kE\\dic_noun_correction.txt");
	}
	
}
