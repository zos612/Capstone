package algorithms;

import java.util.ArrayList;

public class SentimentDocument {

	private ArrayList<SentimentEojeol> sentDoc = null;
	
	public void setSentDoc(ArrayList<SentimentEojeol> sentDoc){
		this.sentDoc = sentDoc;
	}
	public ArrayList<SentimentEojeol> getSentDoc(){
		return sentDoc;
	}
}
