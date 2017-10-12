package database;
import java.sql.*;

public class ModelCheck {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/informoney?autoReconnect=true&useSSL=false";

	static final String USERNAME = "root";
	static final String PASSWORD = "111111";
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
	public void search(String model){
		int rowNum = 0;
		try{
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		//System.out.println("\n- MySQL Connection OK");
		stmt = conn.createStatement();
		String sql = "select * from modelcheck where model=?";
		pstmt = conn.prepareStatement(sql);  // prepareStatement에서 해당 sql을 미리 컴파일한다.
		pstmt.setString(1, model);
		rs = pstmt.executeQuery();
		if(rs.next()){
			System.err.println("등록된 모델입니다.");
			System.exit(0);
		
		}else {
			insert(model);
		}
		
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
	public void insert(String model){
		int rowNum = 0;
		try{
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		//System.out.println("\n- MySQL Connection OK");
		stmt = conn.createStatement();
		String sql = "insert into modelcheck (model) values('" + model + "')";
		rowNum = stmt.executeUpdate(sql);
		
		
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
