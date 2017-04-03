//필요한 패키지 다운로드하는 곳
//Jsoup라이브러리 ->http://rhkdvy1200.tistory.com/entry/Jsoup%EB%A1%9C-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0
//komoran라이브러리 -> http://shineware.tistory.com/entry/KOMORAN-ver-20-beta-%EC%9E%90%EB%B0%94-%ED%95%9C%EA%B5%AD%EC%96%B4-%ED%98%95%ED%83%9C%EC%86%8C-%EB%B6%84%EC%84%9D%EA%B8%B0

//import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import java.util.List;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//import kr.co.shineware.nlp.komoran.*;
//import kr.co.shineware.nlp.komoran.core.analyzer.Komoran;
//import kr.co.shineware.util.common.model.Pair;

public class Main {
	public static void main(String[] args)throws IOException{
		
    	String item;
    	final String naverURL = "http://shopping.naver.com/search/all.nhn?where=all&frm=NVSCTAB&query=";
    	Scanner scan = new Scanner(System.in);
    	System.out.print("원하는 아이템 입력");
    	item = scan.nextLine();
    	String queryURL = naverURL+item;
    	
//		//네이버 쇼핑 샘플 상품평 저장
//		FileWriter fw1 = new FileWriter("C:\\Users\\lee\\jsoup\\out1.txt");
//		//네이버 쇼핑 샘플 상품평을 형태소로 나누어서 저장
//		FileWriter fw2 = new FileWriter("C:\\Users\\lee\\jsoup\\out2.txt");
		//connet한 url를 파싱해온다.
		Document doc = Jsoup.connect(queryURL).get();
		//지정한 url에서 select하여 원하는 데이터만 추출한다. 참고로 div.atc는
		Elements titles = doc.select("div.title");
		
		//Elements titles = doc.select("div.atc");
		//의미사전 모델을 지정한 한글 형태소 분석기 komoran을 생성한다.
		//Komoran komoran = new Komoran("C:\\Users\\lee\\Desktop\\model\\models-full");//
		

//		komoran.setFWDic("user_data/fwd.user");
//		komoran.setUserDic("user_data/dic.user");
		
		//출력 확인 테스트를 위해서 이클립스 콘솔창에  파싱한 리뷰 출력
//		for(Element e: titles){
//			System.out.println("review: " + e.text());
//		}
		//출력 확인 테스트를 위해서 이클립스 콘솔창에  리뷰의 형태소 분석 결과 출력
//		for(Element e: titles){
//		List<List<Pair<String,String>>> result = komoran.analyze(e.text());//입력된 sentence의 분석 결과를 반환
//		for (List<Pair<String, String>> eojeolResult : result) {
//			for (Pair<String, String> wordMorph : eojeolResult) {
//				System.out.println(wordMorph);
//			}
//			System.out.println();
//			}
//		}
//		//파일에  파싱한 리뷰 쓰기
//		int i=1;
//		for(Element e: titles){
//			String data = i++ +" 번째 리뷰\r\n";
//	        fw1.write(data);
//            fw1.write(e.text());
//            fw1.write("\r\n\r\n");
//		}
//		//파일에 리뷰 형태소 분석 결과 쓰기 
//		//파일 저장 형식 [단어][tab][품사]
//		int j=1;
//		for(Element e: titles){
//			List<List<Pair<String,String>>> result = komoran.analyze(e.text());//입력된 sentence의 분석 결과를 반환
//			for (List<Pair<String, String>> eojeolResult : result) {
//				for (Pair<String, String> wordMorph : eojeolResult) {
//					String data = j++ +" 번째  형태소 결과\r\n";
//					fw2.write(data);
//					fw2.write(wordMorph.toString());
//					fw2.write("\r\n\r\n");
//					
////					System.out.println(wordMorph);
//				}
//				System.out.println();
//				}
//			}
//		
//	
//		
//		fw2.close();
//		fw1.close();
		}
}



