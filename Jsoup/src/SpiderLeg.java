import java.util.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	// Just a list of URLs
	private List<String> links = new LinkedList<String>();
	// This is our web page, or in other words, our document
	private Document htmlDocument;  
	
	//a[href]를 이용하여 url페이지를 지정하고 사용자 url를 추가한다.
	//Give it a URL and it makes an HTTP request for a web page
	public boolean crawl(String url){
		try{
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			
			Document doc = Jsoup.connect(url).get();//제품 타이틀
			
			//지정한 url에서 select하여 원하는 데이터만 추출한다. 참고로 div.atc는
			Elements list = doc.select("li._model_list div.info a.tit");
			
			String BasicHref = "http://shopping.naver.com";
			String reviewURL = null;

			
			
			
			
			for(Element e: list){
				 
				 System.out.println("모델명 : " + e.text());
				 System.out.println();
				 String href = e.toString();
				 int indexOfFirst = href.indexOf("/detail");
				 int indexOfLast = href.indexOf("target");
				 System.out.println("URL : " + BasicHref+href.substring(indexOfFirst,indexOfLast-2));
				 System.out.println();
				 reviewURL = BasicHref+href.substring(indexOfFirst,indexOfLast);
				 Document reviewDoc = Jsoup.connect(reviewURL).get();//제품 리뷰
				 Elements review_detail = reviewDoc.select("ul.lst_review div.atc_area div.atc");
					//ul._review_list  div.atc_area div.atc
					
					for(Element l : review_detail){
						System.out.println(l.text());	
					}
					
					System.out.println();	
				
			}
			
			
			
			

			
		      if(connection.response().statusCode() == 200){ // 200 is the HTTP OK status code indicating that everything is great.
				System.out.println("\n**Visiting** Received web page at " + url);
				
			}
			
			if(!connection.response().contentType().contains("text/html")){
				System.out.println("**Failure** Retrieved something other than HTML");
				return false;
				
			}
		//A list of Elements, with methods that act on every element in the list.		
		Elements linksOnPage = htmlDocument.select("a[href]");
		
		//Element is A HTML element consists of a tag name, attributes, and child nodes 
		//From an Element, you can extract data, traverse the node graph, and manipulate the HTML.
		System.out.println("Found (" + linksOnPage.size() + ") links");
		
		
		for(Element link : linksOnPage){
			this.links.add(link.absUrl("href"));
			}
//		for(int i = 0; i<258 ; i++){
//		String str = this.links.get(i);
//		System.out.println(" test "+str);
//		}
		return true;
		
		}catch(IOException ioe){
			return false;
		}
		
	
	}
	//Tries to find a word on the page
	//make an HTTP request and parse the page
	//search for word
	//return all the links on the page.
	public boolean searchForWord(String searchWord){
		if(this.htmlDocument==null){
			System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;	
		}
		System.out.println("Searching for the word " + searchWord + "...");
		String bodyText = htmlDocument.body().text();
		return bodyText.toLowerCase().contains(searchWord.toLowerCase());
		
		
	}
	// Returns a list of all the URLs on the page
	public List<String> getLinks(){
		return this.links;
	}


}
