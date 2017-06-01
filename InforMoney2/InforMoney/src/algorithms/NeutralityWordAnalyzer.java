package algorithms;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NeutralityWordAnalyzer {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/informoney";

	static final String USERNAME = "root";
	static final String PASSWORD = "111111";
	

	private String sentWord;
	private int sentValue;
	
	public void setSentWord(String sentWord) {
		this.sentWord = sentWord;

		}
	public void setSentValue(int sentValue) {
		this.sentValue = sentValue;
		}
	
	public int getSentValue() {
		return this.sentValue;
		}
	
	public void conn(String categoryCurrent, String featureCurrent, String sentWord) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			//System.out.println("\n- MySQL Connection OK");
			stmt = conn.createStatement();
			
			String query = "select * from NeutralityAnalysis"; //category, feature, sentWord, sentValue
			rs = stmt.executeQuery(query);
			
			while(rs.next()){
				String DBcategory = rs.getString("category");
				String DBfeature = rs.getString("feature");
				String DBsentWord = rs.getString("sentWord");
				int DBsentValue = rs.getInt("sentValue");
				if(categoryCurrent.equals(DBcategory) && featureCurrent.equals(DBfeature) && sentWord.equals(DBsentWord)){
					sentValue = DBsentValue;
					break;
				}
			}
			
			stmt.close();
			conn.close();
		}catch(SQLException se1){
			se1.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		//System.out.println("\n\n- MySQL Connection Close");
	}
	
	
}
