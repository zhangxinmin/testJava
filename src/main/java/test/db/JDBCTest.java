package test.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCTest {
//public static final String url="jdbc:srdbsql://10.167.210.206:9999/srdb";
//public static final  String user="tz";
//public static final String pass="tz";
public static final String url="jdbc:srdbsql://10.180.100.201:1975/cfdb";
public static final  String user="chp30";
public static final String pass="chp30";
public static void main(String[] args) throws Exception {
	System.out.println(getConn());
}
public static Connection getConn() throws Exception{
	Class.forName("org.srdbsql.Driver");
    Connection conn = DriverManager.getConnection(url, user,pass);
    return conn;
}
private void test(){
	
}

}
