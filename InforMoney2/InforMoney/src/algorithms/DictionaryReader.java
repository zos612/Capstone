package algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DictionaryReader {
	
	public static Dictionary sentimentDic = null;
	
	public static Dictionary VACor = null;
	
	public static Dictionary nounCor = null;
	
	public DictionaryReader(){
		sentimentDic = new Dictionary();
		VACor = new Dictionary();
		nounCor = new Dictionary();
		
	}
	public void init() throws UnsupportedEncodingException, FileNotFoundException, IOException{
		sentimentDic.readDic(".\\data\\kE\\dic_sentiment_value.txt");
		VACor.readDic(".\\data\\kE\\dic_correction_verb_adjective.txt");
		nounCor.readDic(".\\data\\kE\\dic_correction_noun.txt");
	}
	
}
