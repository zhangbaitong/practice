package com.zbt.jdk7;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class JDBC41Test {

	public static void main(String[] args) {
		//Connection，ResultSet 和 Statement 都实现了Closeable 接口，支持自动关闭资源
//		try (Statement stmt = con.createStatement()){  
//		} 
		//引入RowSetFactory接口和RowSetProvider类，可以创建JDBC driver支持的各种 row sets
		//createCachedRowSet
		//createFilteredRowSet
		//createJdbcRowSet
		//createJoinRowSet
		//createWebRowSet
		RowSetFactory myRowSetFactory = null;  
		JdbcRowSet jdbcRs = null;  
		try {  
		  
		  myRowSetFactory = RowSetProvider.newFactory();//用缺省的RowSetFactory 实现  
		  jdbcRs = myRowSetFactory.createJdbcRowSet();  
		    
		  //创建一个 JdbcRowSet 对象，配置数据库连接属性  
		  jdbcRs.setUrl("jdbc:myDriver:myAttribute");  
		  jdbcRs.setUsername("username");  
		  jdbcRs.setPassword("password");  
		  
		  jdbcRs.setCommand("select ID from TEST");  
		  jdbcRs.execute();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
