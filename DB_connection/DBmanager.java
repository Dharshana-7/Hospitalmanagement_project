package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import model.Patient;


public class DBmanager {
	String url="jdbc:mysql://localhost:3306/hospital_DB";
	String username="root";
	String password="Dharshana07$";
	static Connection conn;
	
	/*static
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");	//Java to register the driver with the JDBC framework so that you can connect to a MySQL database.
		}
		catch(Exception e)			//if the driver class is not found, it will throw a ClassNotFoundException.						
		{							//The catch block prints the error message if any exception occurs.
			System.out.println(e);
		}
	}	*/
	
	
	public DBmanager() throws SQLException, ClassNotFoundException {		//constructor
		Class.forName("com.mysql.cj.jdbc.Driver");
        conn=DriverManager.getConnection(url,username,password);
    }

	
	
	public void createtable() throws ClassNotFoundException, SQLException
	{
		try
		{
		Statement stmt=conn.createStatement();
		String query="create table patientqueue("+"Admission int," + "name varchar(50)," + "age int," + "healthissue varchar(100)," + "priority varchar(10)," + "admitted_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")";
		stmt.execute(query);
		System.out.println("queue created");
		}
		catch(SQLSyntaxErrorException e)
		{
			if(e.getMessage().contains("doesn't exist"))
			{
				System.out.println("Error: Table doesn't Exist,\nCreate table");
			}
			else
			{
				System.out.println("Unexpected Sql Error: "+ e.getMessage());
			}
		}
		catch(Exception e)
		{
			System.out.println("Error Retriving Data: " +e.getMessage());
		}
	}

	
	
	public void inserttable(Patient p) throws ClassNotFoundException, SQLException
	{
	
		try
		{
		String query="insert into patientqueue(Admission,name,age,healthissue,priority,admitted_time) values(?,?,?,?,?,?)";  //?
		PreparedStatement stmt=conn.prepareStatement(query);
		stmt.setInt(1, p.Admission);
		stmt.setString(2, p.name);
		stmt.setInt(3, p.age);
		stmt.setString(4, p.healthissue);
		stmt.setString(5, p.priority);
		stmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));

		int rows=stmt.executeUpdate();
	
		System.out.println("Number of rows inserted "+rows);
		System.out.println("Patient added to queue and database");
		}
		catch(SQLSyntaxErrorException e)
		{
			if(e.getMessage().contains("doesn't exist"))
			{
				System.out.println("Error: Table doesn't Exist,\nCreate table");
			}
			else
			{
				System.out.println("Unexpected Sql Error: "+ e.getMessage());
			}
		}
		catch(Exception e)
		{
			System.out.println("Error Retriving Data: "+ e.getMessage());
		}
	}
	
	
	
	public void retrivetable() throws ClassNotFoundException, SQLException
	{
		try											//if Error occurs for table doesn't exist,the program stops and it will not display the main menu. 				
		{					//without try catch block the menu will not be displayed and program get stopped.
		
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
		catch(SQLSyntaxErrorException e)
		{
			if(e.getMessage().contains("doesn't exist"))
			{
				System.out.println("Error: Table doesn't Exist,\nCreate table");
			}
			else
			{
				System.out.println("Unexpected Sql Error: "+ e.getMessage());
			}
		}
		catch(Exception e)
		{
			System.out.println("Error Retriving Data: " +e.getMessage());
		}
		
	}
	
	
	
	public void deletePatient(int Admission) throws Exception {
		try
		{
	    String query="Delete from patientqueue where Admission= ?";
	    PreparedStatement stmt = conn.prepareStatement(query);
	    stmt.setInt(1, Admission);
	    stmt.executeUpdate();
		}
		catch(SQLSyntaxErrorException e)
		{
			if(e.getMessage().contains("doesn't exist"))
			{
				System.out.println("Error: Table doesn't Exist,\nCreate table");
			}
			else
			{
				System.out.println("Unexpected Sql Error: "+ e.getMessage());
			}
		}
		catch(Exception e)
		{
			System.out.println("Error Retriving Data: " +e.getMessage());
		}
	}
	
	
	
	public void searchpatientbyToken(String token) throws SQLException
	{
		try
		{
		String query="select * from patientqueue where Admission like ? or name like ? or age like ? or healthissue like ? or priority like ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		String likePattern = "%" + token + "%";
	    stmt.setString(1, likePattern);
	    stmt.setString(2, likePattern);
	    stmt.setString(3, likePattern);
	    stmt.setString(4, likePattern);
	    stmt.setString(5, likePattern);
	    ResultSet rs = stmt.executeQuery();
	    
	    System.out.println("Search Results:");
        System.out.println("Admission\tName\t\tAge\tHealth Issue\t\tPriority");

        while (rs.next()) {
            System.out.println(
            	rs.getInt("Admission") + "\t\t" +
                rs.getString("name") + "\t" +
                rs.getInt("age") + "\t" +
                rs.getString("healthissue") + "\t\t" +
                rs.getString("priority")
            );
        }
    } 
	catch (SQLException e) 
		{
        System.out.println("Error during search: " + e.getMessage());
		}
		
	}
	
	
	public void deleteByToken(String token) throws SQLException {
		try
		{
	    String query = "DELETE FROM patientqueue WHERE name = ?";
	    PreparedStatement stmt = conn.prepareStatement(query);
	    stmt.setString(1, token);
	    int rows = stmt.executeUpdate();

	    if (rows > 0) {
	        System.out.println(" Deleted " + rows + " patient(s) with name: " + token);
	    } else {
	        System.out.println(" No patient found with name: " + token);
	    }
		}
		catch(SQLSyntaxErrorException e)
		{
			if(e.getMessage().contains("doesn't exist"))
			{
				System.out.println("Error: Table doesn't Exist,\nCreate table");
			}
			else
			{
				System.out.println("Unexpected Sql Error: "+ e.getMessage());
			}
		}
		catch(Exception e)
		{
			System.out.println("Error Retriving Data: " +e.getMessage());
		}
	}
	
	
	public void close() throws SQLException 
	{
		conn.close();
	}
	
	}

