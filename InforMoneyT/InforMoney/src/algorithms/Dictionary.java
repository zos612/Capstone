package algorithms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class Dictionary {
	
	private Hashtable<String, String> dictionary;
	//생성자
	public Dictionary() {
		dictionary = new Hashtable<String, String>();
	}
	
	public Dictionary(String dictionaryFileName) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		dictionary = new Hashtable<String, String>();
		
		this.readDic(dictionaryFileName);
	}
	
	public String get(String item) {
		return dictionary.get(item);
	}
	
	/**
	* 감정 단어 사전을 데이터 파일에서 해시 테이블로로드합니다.
	* 사전의 파일 형식은 다음과 같아야합니다 : "ITEM \t CONTENT \n"
	* @param dictionaryFileName - 사전 분석 된 사전 파일의 경로
	* UnsupportedEncodingException가 throw됩니다.
	* FileNotFoundException를 throw합니다.
	* IOException를 throw합니다
	*/
	
	public void readDic(String dictionaryFileName) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		dictionary.clear();
		String str = "";
		
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFileName), "UTF-8"));

		while ((str = in.readLine()) != null) {
			str.trim();
			if (str.equals("")) {
				continue;
			}
			StringTokenizer tok	= new StringTokenizer(str, "\t");
			String key = tok.nextToken();
			String value = "";
			while (tok.hasMoreTokens()) {
				value += tok.nextToken() + "\n";
			}
			dictionary.put(key, value.trim());
		}
	}
}
