import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;
public class Test {
	static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	static final String MySQL_CONNC = "jdbc:mysql://localhost:3306";
	static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	static final String ORACLE_CONNC = "jdbc:oracle:thin:hr/hr@localhost:1521/XE";
	public static void main(String[] args) throws Exception {
		testOracle();
	}
	public static void testOracle() throws Exception {
		Class.forName(ORACLE_DRIVER);
		Connection conn = DriverManager.getConnection(ORACLE_CONNC, "hr", "hr");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from bbc");
		ResultSetMetaData rsMetaData = rs.getMetaData();
	    int numberOfColumns = rsMetaData.getColumnCount();
		System.out.println("+--------------------+");
		while (rs.next()) {
			for(int i=0;i<numberOfColumns;i++){
				System.out.printf("%-25s",rs.getObject(i+1));
			}
			System.out.println();
		}
		System.out.println("+--------------------+");
		
		stmt.close();
	}
	public static void testMySQL() throws Exception {
		//1. 载入驱动
		Class.forName("com.mysql.jdbc.Driver");
		//2. 通过数据库的地址，用户名和密码连接数据库
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
		//3. 创建一个Java Statement对象，一会儿用来运行SQL命令
		Statement stmt = conn.createStatement();
		//4. 运行SQL命令，并把结果存到实现了ResultSet的结果集中
		ResultSet rs = stmt.executeQuery("show databases");
		
		System.out.println("+--------------------+");
		System.out.println("| Database 数据库            |");
		System.out.println("+--------------------+");
		//5. 通过rs.next()来判断是否还有下一行,有就返回true,并把指针指向这一行
		while (rs.next()) {
			//打印这一行中的第一列信息
			System.out.printf("| %-18s |\n", rs.getObject(1)); 
		}
		System.out.println("+--------------------+");
		//6. 关闭Statment对象【这一步一定要有】
		stmt.close();
	}
	public static void testMySQLSelect() throws Exception {
		Class.forName(MYSQL_DRIVER);
		Connection conn = DriverManager.getConnection(MySQL_CONNC, "root", "");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("show databases");
		System.out.println("+--------------------+");
		
		rs = stmt.executeQuery("select * from test.item");
		ResultSetMetaData rsMetaData = rs.getMetaData();
	    int numberOfColumns = rsMetaData.getColumnCount();
		//System.out.println("+--------------------+");
		while (rs.next()) {
			for(int i=0;i<numberOfColumns;i++){
				System.out.printf("%-20s",rs.getObject(i+1));
			}
			System.out.println();
		}
		//System.out.println("+--------------------+");
		
		stmt.close();
	}
	
	public static void testMySQLSelect2() throws Exception {
		Class.forName(MYSQL_DRIVER);
		Connection conn = DriverManager.getConnection(MySQL_CONNC, "root", "");
		Statement stmt = conn.createStatement();
		Scanner sc=new Scanner(new File("./command.txt"));
		int round=0;
		while(sc.hasNextLine()){
			System.out.println("+-----"+(round++)+"-----+");
			ResultSet rs = stmt.executeQuery(sc.nextLine());
			
			ResultSetMetaData rsMetaData = rs.getMetaData();
		    int numberOfColumns = rsMetaData.getColumnCount();
			//System.out.println("+--------------------+");
			while (rs.next()) {
				for(int i=0;i<numberOfColumns;i++){
					System.out.printf("%-20s",rs.getObject(i+1));
				}
				System.out.println();
			}
			System.out.println("+-----------+\n");
		}
		
		
		stmt.close();
	}
}
