package algorithms;
import java.sql.*;

public class seDB {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/informoney?autoReconnect=true&useSSL=false";

	static final String USERNAME = "root";
	static final String PASSWORD = "111111";
	
	Connection conn = null;
	Statement stmt = null;
		
	public void insertSe(String model ,String feature, String sentWord, int sentValue, String word1, String word2, String word3, String word4) {

		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			//System.out.println("\n- MySQL Connection OK");
			stmt = conn.createStatement();
			String sql = "insert se (model, feature, sentWord, sentValue, word1, word2, word3, word4) values('"+ model + "', '" + 
					feature + "', '" + sentWord + "', '" + sentValue + "', '" + word1 + "', '" + word2 + "', '" + word3 + "', '" + word4 + "')";
			int rowNum =stmt.executeUpdate(sql);
			
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
	
	public void insertTotalScore(String model, int positive, int negative, int neutral ){
		try{
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		//System.out.println("\n- MySQL Connection OK");
		stmt = conn.createStatement();
		String sql = "insert totalScore (model, positive, negative, neutral) values('" + 
				model + "', '" + positive + "', '" + negative + "', '" + neutral + "')";
		int rowNum = stmt.executeUpdate(sql);
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
}
	public void top5Pos() {

		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			//System.out.println("\n- MySQL Connection OK");
			stmt = conn.createStatement();
			String sql = null;
			int rowNum =stmt.executeUpdate(sql);
			
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
	public void initDB(){
			try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			//System.out.println("\n- MySQL Connection OK");
			stmt = conn.createStatement();
			String sql = "delete from se"; 
			stmt.executeUpdate(sql); 
			sql = "delete from totalscore"; 
			stmt.executeUpdate(sql); 
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
	}
	
	
}
