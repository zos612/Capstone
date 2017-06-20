
import java.io.IOException;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Crawling {
	public static void main(String[] args)throws IOException{
		System.out.println("웹 파싱 시작");
    	String item;
    	
    	ArrayList<String> naverShopURL = new ArrayList<String>();
        String base = "http://shopping.naver.com/search/all.nhn?query=";
        String KEYWORD = "가습기";
        
        DTO dto = new DTO();
        insertDAO DB = new insertDAO();
        Crawling crawler = new Crawling();
        IDparsing parse = new IDparsing();
        ManipulateUrl manipulateUrl = new ManipulateUrl();
        
    	
    	//final String query = "http://shopping.naver.com/search/all.nhn?query=가습기";
    	
    	
    	
    	
    	naverShopURL = manipulateUrl.makeUrl("http://shopping.naver.com/detail/detail.nhn?",KEYWORD,parse.getModelId(base, KEYWORD));
    	//카테고리를 파싱한다. 	
    	//crawler.getCategory(base+KEYWORD);
    	//상품 브랜드 이름과 url를 파싱한다. 
    	//for(int i=0;i<naverShopURL.size();i++){
    		
    	//	crawler.getBrandName(naverShopURL.get(2));
    	//	crawler.getUrl(naverShopURL.get(2));
    	//}	
			
    	
    	//리뷰 파싱 기능
    		crawler.getProductReview(naverShopURL.get(2));
    		
    	//DB에 브랜드를 넣는다.
    	//for(int i=0;i<naverShopURL.size();i++){
    	//dto.setModel(crawler.getBrandName(naverShopURL.get(2)));
    	//}
    	//DB에 url를 넣는다. 
    	//for(int i=0;i<naverShopURL.size();i++){
        //	dto.setUrl(crawler.getUrl(naverShopURL.get(2)));
        //}
		//http://shopping.naver.com/search/all.nhn?query=%EA%B0%80%EC%8A%B5%EA%B8%B0
    	//http://shopping.naver.com/search/all.nhn?query=

		
		}
	
	
	void getCategory(String url) throws IOException{
		String trim;
		int endIndex;
		Document doc = Jsoup.connect(url).get();
		Elements category = doc.select("ul.goods_list span.depth ");
		trim = category.text();
		endIndex = trim.indexOf('>');
		System.out.println("category "+trim.substring(0, endIndex));
			
	}
	
	String getBrandName(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		Elements brandName = doc.select("div.h_area h2");
//		if(brandName != null)
//			System.out.println("상품없음");
		System.out.println(brandName.text());
		if(brandName == null)
			return "상품없음";
		else
			return brandName.text();
			
	}
	String getUrl(String url){
		System.out.println(url);
		return url;
		
	}
	void getProductReview(String url) throws IOException{
		
    	int cnt1;
    	int cnt2=1;
    	
    	
    	for(cnt1 = 1; cnt1<=100 ; cnt1++){
    		url = url + "&page="+cnt1;
		Document doc = Jsoup.connect(url).get();
		//Document doc_query = Jsoup.connect(query).get();
		
		Elements scraping = doc.select("div.atc");
		
		for(Element e : scraping){
		System.out.println(cnt2+e.text());
		cnt2++;
		}
		
    	}
	}
	
	
}

