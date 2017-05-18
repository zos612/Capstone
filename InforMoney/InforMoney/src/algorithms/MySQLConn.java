package algorithms;
import java.sql.*;

public class MySQLConn {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/informoney";

	static final String USERNAME = "root";
	static final String PASSWORD = "111111";
	
	public void conn(String feature, String sentWord, int sentValue, String word1, String word2, String word3, String word4) {

		Connection conn = null;
		Statement stmt = null;
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			//System.out.println("\n- MySQL Connection OK");
			stmt = conn.createStatement();
			
			int rowNum =stmt.executeUpdate("insert se (feature, sentWord, sentValue, word1, word2, word3, word4) values('" + 
			feature + "', '" + sentWord + "', '" + sentValue + "', '" + word1 + "', '" + word2 + "', '" + word3 + "', '" + word4 + "');");
			
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
	
	/*public void insert(String Feature, String SentWord, int SentValue, String word1, String word2, String word3, String word4){
		try{
		stmt = conn.createStatement();
		
		int rowNum =stmt.executeUpdate("insert se (feature, sentWord, sentValue, word1, word2, word3, word4)"
				+ "values(feature + sentWord + sentValue + word1 + word2 + word3 + word4);");
		
		}catch(SQLException se1){
			se1.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
		}
	}*/
}
