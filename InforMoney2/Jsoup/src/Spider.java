import java.util.*;

public class Spider {
	private static final int MAX_PAGES_TO_SEARCH = 10;
	// contains unique entries. In other words, no duplicates
	private Set<String> pagesVisited = new HashSet<String>();
	// storing a bunch of URLs we have to visit next.
	private List<String> pagesToVisited = new LinkedList<String>();
	// We get the first entry from pagesToVisit, make sure that URL isn't in our set of URLs we visited, and then return it. 
	private String nextUrl(){
		String nextUrl;
		do{
			nextUrl = this.pagesToVisited.remove(0);
			
		}
		// we keep looping through the list of pagesToVisit and returning the next URL.
		while(this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		
		return nextUrl;
		
	}
	
	public void search(String url, String searchWord){
		while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH){
			String currentUrl;
			String nextUrl;
			SpiderLeg leg = new SpiderLeg();
			if(this.pagesToVisited.isEmpty()){
				currentUrl = url;
				this.pagesVisited.add(url);
			}
			else{
				currentUrl = this.nextUrl();
			}
			
			leg.crawl(currentUrl);
			boolean success = leg.searchForWord(searchWord);
			if(success){
				System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
				nextUrl = nextUrl();
				System.out.print("nextUrl list : " + nextUrl);
				
                break;
			}
			this.pagesToVisited.addAll(leg.getLinks());
			
			
		}
		System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
	}
	

	
	
	
	
	
}
