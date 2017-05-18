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
    	final String naverURL = "http://shopping.naver.com/search/all.nhn?query=";
    	Scanner scan = new Scanner(System.in);
    	System.out.print("원하는 아이템 입력 ");
    	item = scan.nextLine();
    	String queryURL = naverURL+item;
        Spider spider = new Spider();
        spider.search(queryURL, item);
    }
}