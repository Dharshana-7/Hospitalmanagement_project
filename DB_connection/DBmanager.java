package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBmanager {
	String url="jdbc:mysql://localhost:3306/hospital_DB";
	String username="root";
	String password="Dharshana07$";
	static Connection conn;
	
	static
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");	//Java to register the driver with the JDBC framework so that you can connect to a MySQL database.
		}
		catch(Exception e)			//if the driver class is not found, it will throw a ClassNotFoundException.						
		{							//The catch block prints the error message if any exception occurs.
			System.out.println(e);
		}
	}
	
	
	public DBmanager() throws SQLException {
        conn=DriverManager.getConnection(url,username,password);
    }

	
	public static void createtable() throws ClassNotFoundException, SQLException
	{
		
		Statement stmt=conn.createStatement();
		String query="create table patientqueue("+"Admission int," + "name varchar(50)," + "age int," + "healthissue varchar(100)," + "priority varchar(10)," + "admitted_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")";
		stmt.execute(query);
		System.out.println("queue created");
	}

	
	public static void inserttable(Patient p) throws ClassNotFoundException, SQLException
	{
	
		
		String query="insert into patientqueue values(?,?,?,?,?,?)";
		PreparedStatement stmt=conn.prepareStatement(query);
		stmt.setInt(1, p.Admission);
		stmt.setString(2, p.name);
		stmt.setInt(3, p.age);
		stmt.setString(4, p.healthissue);
		stmt.setString(5, p.priority);
		stmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));

		int rows=stmt.executeUpdate();
	
		System.out.println("Number of rows inserted "+rows);
	}


	public static void retrivetable() throws ClassNotFoundException, SQLException
	{
		
		Statement stmt=conn.createStatement();
		String query="select * from patientqueue";
		
		ResultSet rs=stmt.executeQuery(query);
	
		System.out.println("Admission no"+"\t"+"Name"+"\t\t"+"Age"+"\t"+"healthissue"+"\t\t"+"priority");
		System.out.println("------------------------------------------------------------------------");
		while(rs.next()) 
		{
			System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t"+rs.getInt(3)+"\t"+rs.getNString(4)+"\t\t"+rs.getString(5));

		}
	}

	public static void close() throws SQLException 
	{
		conn.close();
	}
	
	}

