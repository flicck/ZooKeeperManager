package cn.zkManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Random;

public class Insert {
	static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	static final String DB_URL="jdbc:mysql://localhost:3306/appdb?characterEncoding=utf-8";
	static final String USER="root";
	static final String Password="mysqladmin";
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		System.out.println("连接数据库---");
		Connection conn=DriverManager.getConnection(DB_URL,USER,Password);
		System.out.println("创建查询----");
		Statement stmt = conn.createStatement();
		int temppassword;
		double price;
		String sql;
		Random r = new Random();
		try {
			for (int i = 0; i < 200; i++) {
				temppassword = (int) (Math.random() * (9999999 - 0 + 1) + 1000000);
				System.out.println(100+i);
				System.out.println(temppassword);
				
				sql = "insert into user_info(nick_name,upassword) value('switch" + (100+i) + "','"+temppassword+"')";
				stmt.execute(sql);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
