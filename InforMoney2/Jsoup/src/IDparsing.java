import java.io.IOException;
//import org.jsoup.helper.Validate;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class IDparsing {

    
    void print_model_id(ArrayList<String> getList){
    	String str;
    	int cnt;
    	cnt = getList.size();
        for(int i = 0; i<cnt; i++){
        	str = getList.get(i);
        	System.out.println(str);
        
        }
    }
    
    ArrayList<String> getModelId(String base , String KEYWORD) throws IOException{
    	
    	String str;
    	String nv_mid;
    	String subString;
    	String model_Id;
    	int indexOfFirst,indexOfLast;
        String url = base + KEYWORD;
        print("Fetching %s", url);
        String attr;
        Document doc = Jsoup.connect(url).get();
        int num;
        Elements mid_id = doc.select("._productLazyImg");
        ArrayList<String> list = new ArrayList<String>();
    	
    		for (Element l : mid_id) {
        	
        	nv_mid = l.attr("data-original");
        	indexOfFirst = nv_mid.indexOf("main_"); 
        	indexOfLast = nv_mid.indexOf('.');
			
			subString = nv_mid.substring(indexOfFirst+13,nv_mid.length());
			
			 
        	indexOfLast = subString.indexOf('.');
        	
        	model_Id = subString.substring(0,indexOfLast);
        	//System.out.println("모델 아이디 :"+model_Id);
        	if(!model_Id.equals("ing")){
        		list.add(model_Id);
    			}
    		}	
    		return list;
        	}	
			
    	
    
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}



