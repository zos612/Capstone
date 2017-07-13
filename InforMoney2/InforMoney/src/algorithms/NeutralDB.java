package algorithms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class NeutralDB {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/informoney?autoReconnect=true&useSSL=false";

	static final String USERNAME = "root";
	static final String PASSWORD = "111111";
	
	Connection conn = null;
	Statement stmt = null;
	
	public void neutralDB(){
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			//System.out.println("\n- MySQL Connection OK");
			stmt = conn.createStatement();
			String sql = "TRUNCATE TABLE informoney.NeutralityAnalysis";
			stmt.executeUpdate(sql); 
			
			String filename = "C:\\Users\\yong\\Desktop\\dataset.csv"; 
			 String tablename = "informoney.NeutralityAnalysis"; 
			sql = "LOAD DATA INFILE \"" + filename + "\" INTO TABLE " + tablename;
			 stmt.executeUpdate(sql);
			sql = "INTO TABLE informoney.NeutralityAnalysis";
			stmt.executeUpdate(sql); 
			sql = "ENCLOSED BY '\"'";
			stmt.executeUpdate(sql); 
			sql = "LINES TERMINATED BY '\n'";
			stmt.executeUpdate(sql); 
			sql = "IGNORE 1 LINES";
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
