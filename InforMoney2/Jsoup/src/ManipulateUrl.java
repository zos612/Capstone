import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.*;


public class ManipulateUrl{
	
	
	ArrayList<String> makeURL = new ArrayList<String>(); 

	public ArrayList<String> makeUrl(String baseUrl , String query , ArrayList<String> list){
		int cnt;
		String url;
		String nv_mid;
		cnt = list.size();
		for(int i=0 ; i<cnt; i++){
		try {
       	  url= baseUrl + "nv_mid="+list.get(i)+"&frm=&query="+query;
       	  //System.out.println(url);
       	  makeURL.add(url);
       	  } catch (Exception e) {
           e.printStackTrace();
           }
	  
		}
		return makeURL;
		}
	}