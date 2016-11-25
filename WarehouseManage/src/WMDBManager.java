
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author how32
 */
public class WMDBManager {
    //데이터베이스 접근
    private static WMDBManager db_instance = null;
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    private WMDBManager() {}
    
    public static WMDBManager getInstance() {
        if(db_instance == null) {
            db_instance = new WMDBManager();
        }
        
        return db_instance;
    }
    
    public void dbSelect() {
        //ArrayList 반환 ?
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/board?" +
                    "characterEncoding=utf-8", "root","apmsetup"); //접속할 db, id, password
            stmt = conn.createStatement();
            rs = stmt.executeQuery(""); //쿼리 동작시킴
            
            if(rs == null) { System.out.println("내용 없음"); }
            while(rs.next()) {
                //한 행 반환
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) {conn.close();}
                if(stmt!=null) {stmt.close();}
                if(rs!=null) {rs.close();}
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
