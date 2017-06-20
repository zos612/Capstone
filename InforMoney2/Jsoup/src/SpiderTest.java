import java.util.Scanner;
public class SpiderTest
{
    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args)
    {
    	String item;
    	final String naverURL = "http://shopping.naver.com/search/all.nhn?where=all&frm=NVSCTAB&query=";
    	//http://shopping.naver.com/search/all.nhn?where=all&frm=NVSCTAB&query=
    	Scanner scan = new Scanner(System.in);
    	//System.out.print("원하는 상품을 입력하세요");
    	//item = scan.nextLine();
    	String queryURL = naverURL+"가습기";
        Spider spider = new Spider();
        spider.search(queryURL,"가습기");
        
    }
}