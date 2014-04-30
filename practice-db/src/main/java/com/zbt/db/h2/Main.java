package com.zbt.db.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

	/**
	 * java -cp h2-1.4.177.jar org.h2.tools.Server
	 * 然后选择Generic H2 (Embedded)
	 * 执行对应sql
	 * 运行测试方法
	 * @throws Exception
	 */
	public static void testConnection() throws Exception{
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		// add application code here     
		Statement stmt = conn.createStatement();    
		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST ");     
		while(rs.next()) {       
			System.out.println(rs.getInt("ID")+","+rs.getString("NAME"));  
		}    
		conn.close();
	}
	
	public static void testTCPConnection() throws Exception{
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
		// add application code here     
		Statement stmt = conn.createStatement();    
		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST ");     
		while(rs.next()) {       
			System.out.println(rs.getInt("ID")+","+rs.getString("NAME"));  
		}    
		conn.close();
	}
	
	public static void testMEMConnection() throws Exception{
		Class.forName("org.h2.Driver");
		//指定一个内存数据库的名称即可
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
		//通过TCP协议访问另一个机器中的内存数据库
		//Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/mem:test", "sa", "");
		// add application code here     
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE TEST_MEM(ID INT PRIMARY KEY,NAME VARCHAR(255));");
		stmt.executeUpdate("INSERT INTO TEST_MEM VALUES(1, 'Hello_Mem');");
		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST_MEM");   
		while(rs.next()) {       
			System.out.println(rs.getInt("ID")+","+rs.getString("NAME"));  
		}    
		conn.close();
	}
	
	public static void main(String[] args) throws Exception {
		//testConnection();
		//testTCPConnection();
		testMEMConnection();
	}

}
