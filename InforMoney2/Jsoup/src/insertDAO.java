import java.sql.Connection;
import java.sql.DriverManager;
 
//import com.mysql.jdbc.Statement;
import java.sql.Statement;
 
public class insertDAO {
	public static void main(String[] args) {
		//System.out.println("Starting....");
  
  }
  public static boolean create(DTO dto) throws Exception {
 
   boolean flag = false;
   Connection con = null;
   Statement stmt = null; // 데이터를 전송하는 객체
   String model = dto.getModel();
   String url = dto.getUrl();
   
   String sql = "INSERT INTO model_url(model,url) VALUES";
 
   try {
    /*한글처리를 위해
     * 이클립스와 데이터베이스 설치시 한글처리를 미리 해주면 코드에서 한글처리
     * 안해도 됩니다.
     * */
    sql += "('" + new String(model.getBytes(), "ISO-8859-1") + "','"  
      + new String(url.getBytes(), "ISO-8859-1") + "','";
      
 
    //동적으로 클래스를 생성하는것도 목적이지만 동적으로 생성될때 DriverManager에 해당 클래스를 등록시키는 목적도 있습니다.
    Class.forName("com.mysql.jdbc.Driver");
    
    con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/scrapy", "root", "458658");
    System.out.println("connection ok");
    stmt = (Statement) con.createStatement();
    stmt.executeUpdate(sql);
 
    flag = true;
   } catch (Exception e) {
    System.out.println(e);
    flag = false;
   } finally {
 
    try {
 
     if (stmt != null)
      stmt.close();
     if (con != null)
      con.close();
    } catch (Exception e) {
     System.out.println(e.getMessage());
    }
   }
 
   return flag;
 
  }
}
