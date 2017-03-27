package a_test;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kaist.cilab.db.FileManager;
import kaist.cilab.db.QueryManager;
import kaist.cilab.parser.berkeleyadaptation.BerkeleyParserWrapper;
import kaist.cilab.parser.corpusconverter.sejong2treebank.sejongtree.ParseTree;
import kaist.cilab.parser.dependency.DTree;
import kaist.cilab.parser.psg2dg.Converter;
import kaist.cilab.tripleextractor.util.Configuration;

import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.InformalSentenceFilter.InformalSentenceFilter;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.SentenceSegmentor.SentenceSegmentor;

import java.util.StringTokenizer;



public class Parser_Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Workflow workflow = new Workflow();
		
		String originalText = "소녀시대는 한국을 사랑한다.";
		String sentence = "소녀시대는 한국을 사랑한다.";
		
		//parse result
		String parsResult = "";
		String parsResult_FuncTag = "";
		
		//ParseTree OBJ
		ParseTree pt = null;
		
		//convert Dependency Tree OBJ
		DTree depTree = null;
		
		try{
			workflow.appendPlainTextProcessor(new SentenceSegmentor(), null);
			workflow.appendPlainTextProcessor(new InformalSentenceFilter(), null);
			
			//String document = "한나눔 형태소 분석기는 KLDP사이트에 등록되어 있다.이 물건은 배송이 빨라서 정말 좋네요.흠냐 흠냐 하하 안녕하세요.알ㅇ나ㅓㅇㄴ라ㅣㅣㄴㄹ";
			String document ="이 물건은 배송이 빨라서 정말 좋네요.";
			String document2;
			workflow.activateWorkflow(true);
			workflow.analyze(document);
			document2 = new String(workflow.getResultOfDocument());
			//System.out.println(workflow.getResultOfDocument());
			//System.out.println(document2);
			//StringTokenizer st = new StringTokenizer(document2,"\n");   // 분류할 토큰 "\n" 

			// while(st.hasMoreTokens()){           //hasMoreTokens() 는 토큰이 더있나 알아봄
			//	  System.out.println(st.nextToken());     // nextToken() 은 다음 토큰 리턴
			
			/*
			 * 
			 * CFG Parsing and dependency parsing part
			 * 
			 * */
			
			BerkeleyParserWrapper bpw	= new BerkeleyParserWrapper(Configuration.parserModel);			
			//String str			= sentence;
			//1. parse the sentence
			parsResult = bpw.parse(document);
			//2. convert PSG-> DG
			Converter cv = new Converter();
			//2-1 need to function Tags so we split function tags
			parsResult_FuncTag = Converter.functionTagReForm(parsResult);
			//2-3 delete \n information from parse result
			parsResult_FuncTag = cv.StringforDepformat(parsResult_FuncTag);
			//2-4 convert to store ParseTree OBJ
			pt = new ParseTree(sentence, parsResult_FuncTag, 0, true);		
			//2-5 convert to store Dependency Tree OBJ
			depTree	= cv.convert(pt);
			
			System.out.println(parsResult_FuncTag);
			
			System.out.println("==============================================");
			//System.out.println("원본 문장 : "+ originalText);
			System.out.println("");
			System.out.println("PSG parsing : ");
			System.out.println(parsResult);
			System.out.println("");
			System.out.println("DG parsing : ");
			System.out.println(depTree.toString());
			System.out.println("==============================================");
			// }
			
/*			
			 * 
			 * for database part
			 * 
			 * 
			QueryManager qm = new QueryManager();
			//Store to DB for Parse result
			qm.toInsertDBParse(pt, session);
			//Store to DB for Dependency Result
			qm.toInsertDBDep(depTree, session);*/
			/*
			 * 
			 * for file manage
			 * 
			 * */
			
			FileManager fm = new FileManager();
			fm.makeFile("ParseResult.txt", "Start#\n" + parsResult+ "End#\n" );
			fm.makeFile("Converted.txt", depTree.toString() );
			
			workflow.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		System.out.println("==============================================");
		System.out.println("원본 문장 : "+ originalText);
		System.out.println("");
		System.out.println("PSG parsing : ");
		System.out.println(parsResult);
		System.out.println("");
		System.out.println("DG parsing : ");
		System.out.println(depTree.toString());
		System.out.println("==============================================");
		*/
		workflow.close();  
	}
}
