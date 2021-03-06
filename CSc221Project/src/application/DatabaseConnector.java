package application;

//import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//********************************************************************
//DatabaseConnector.java       Java Foundations
//
//Demonstrates the establishment of a JDBC connector.
//********************************************************************
public class DatabaseConnector{
 //-----------------------------------------------------------------
 //  Establishes the connection to the database and prints an
 //  appropriate confirmation message.
 //-----------------------------------------------------------------
	public static void main (String args[]){
		try{
			Connection conn = null;
			// Loads the class object for the mysql driver into the DriverManager.
			Class.forName("com.mysql.jdbc.Driver");
			// Attempt to establish a connection to the specified database via the
			// DriverManager
			//conn = DriverManager.getConnection("jdbc:mysql://localhost/Student?" + 
			//		"user=root&password=dbvb72^^DATAf2fa1#$");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", 
					"root", "dbvb72^^DATAf2fa1#$");
			if (conn != null){
				System.out.println("We have connected to our database!");
				conn.close();
			}
		} 
		catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		} 
		catch(Exception ex){
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
		} 
}
}
