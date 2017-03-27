package a.algorithms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * 이 클래스는 감정 사전의 데이터 구조입니다.
 * 해시 테이블 자료구조를 사용합니다.
 *
 */

public class SentimentDic {
	private static Hashtable<String, String> dictionary;
	
	//생성자
	public SentimentDic() {
		dictionary = new Hashtable<String, String>();
	}
	
	public SentimentDic(String dictionaryFileName) throws UnsupportedEncodingException, FileNotFoundException, IOException {
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
	
	public static void readDic(String dictionaryFileName) throws UnsupportedEncodingException, FileNotFoundException, IOException {
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
