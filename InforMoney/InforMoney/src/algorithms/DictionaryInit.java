package algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DictionaryInit {
	
	public static Dictionary sentimentDic = null;
	
	public static Dictionary correctionDic = null;
	
	public static Dictionary nounDic = null;
	
	public DictionaryInit(){
		sentimentDic = new Dictionary();
		correctionDic = new Dictionary();
				//SentimentDic.readDic(".\\data\\kE\\dic_sentiment.txt");
	}
	public void init() throws UnsupportedEncodingException, FileNotFoundException, IOException{
		sentimentDic.readDic(".\\data\\kE\\dic_sentiment.txt");
		correctionDic.readDic(".\\data\\kE\\dic_correction.txt");
		
	}
	
}
