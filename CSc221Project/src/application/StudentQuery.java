package application;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentQuery extends Application{

	private static final String URL = "jdbc:mysql://localhost:3306";
	private static final String user = "root";
	private static final String password = "dbvb72^^DATAf2fa1#$";
	private static Map<String, String> probabilitiesMapForPieChart;
	private static double totalStudents;
	private static Scanner input;
	
	public static void main(String[] args) {
		try {
			// Set up our connection
			Connection conn = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, user, password);
			if(conn != null) {
				System.out.println("We have connected to our database!");
				
				// Uncomment Lines 42, 43, and 46 if there is nothing in the local database yet!
				
				// Create our database and switch to it to add tables
				String databaseName = "Student";
				//PreparedStatement createDatabase = conn.prepareStatement("CREATE SCHEMA " + databaseName);
				//createDatabase.execute();
				PreparedStatement useDatabase = conn.prepareStatement("USE " + databaseName);
				useDatabase.execute();
				//StudentQuery.createTables(conn);
				
				// A switch case to input, delete, or print tables
				input = new Scanner(System.in);			
				System.out.println("To add an entry type 1, to delete an entry type 2, and type anything"
						+ " else to just print out database tables.");
				String userInput = input.next();
				switch(userInput) {
					case "1":
						System.out.println("Current data in " + databaseName);
						StudentQuery.displayTables(conn);
						StudentQuery.populateTables(conn);
						break;
					case "2":
						System.out.println("Current data in " + databaseName);
						StudentQuery.displayTables(conn);
						StudentQuery.deleteFromTables(conn);
						break;
					default:
						break;
				}
				System.out.println("Data in " + databaseName);
				StudentQuery.displayTables(conn);
				StudentQuery.displayPieChart(conn);
				conn.close();
				launch(args);
			}
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		} 
		catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
		} 
	}
	
	public static void createTables(Connection conn) {
		try {
		PreparedStatement studentsTable = conn.prepareStatement("CREATE TABLE Students " +
			    "  (studentID INT UNSIGNED NOT NULL AUTO_INCREMENT, " +
			    "  PRIMARY KEY (studentID), firstName varchar(50), " +
			    "  lastName varchar(50), sex varchar(1))");
		studentsTable.execute();
		
		PreparedStatement coursesTable = conn.prepareStatement("CREATE TABLE Courses " +
				"  (courseID INT UNSIGNED NOT NULL, " +
				"  PRIMARY KEY (courseID), courseTitle varchar(50), " +
				"  department varchar(50))");
		coursesTable.execute();

		PreparedStatement classesTable = conn.prepareStatement("CREATE TABLE Classes " +
				"	(classCode INT UNSIGNED NOT NULL, " +
				"	PRIMARY KEY (classCode), courseID INT, studentID INT, " +
				"	year INT, semester varchar(50), GPA varchar(1))");
		classesTable.execute();
		
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void populateTables(Connection conn) {
		try {
			// USE THE CODE COMMENTED OUT TO HARDCODE DATA IF WANTED
			
			/*PreparedStatement insertData = conn.prepareStatement("INSERT Students " + 
					"(firstName, lastName, sex) VALUES (\"Matthew\", \"Williamson\", \"M\")", 
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);*/
			
			System.out.println("To start entering Students type Y. If not, type anything else.");
			while(input.next().equalsIgnoreCase("Y")) {
				String firstName = "\"";
				String lastName = "\"";
				String sex = "\"";
				
				System.out.println("Enter firstName, lastName, and sex. (In that order)");
				firstName += input.next() + "\"";
				lastName += input.next() + "\"";
				sex += input.next() + "\"";
				
				PreparedStatement insertData = conn.prepareStatement("INSERT Students " + 
						"(firstName, lastName, sex) VALUES (" + firstName + ", " + 
						lastName + ", " + sex + ")", 
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);		
				insertData.executeUpdate();
				System.out.println("Continue? Y/N");
				
			}
			
			System.out.println("To start entering Courses type Y. If not, type anything else.");
			while(input.next().equalsIgnoreCase("Y")) {
				int courseID = 0;
				String courseTitle = "\"";
				String department = "\"";
				
				System.out.println("Enter courseID, courseTitle, and department. (In that order)");
				courseID += input.nextInt();
				courseTitle += input.next() + "\"";
				department += input.next() + "\"";
				
				PreparedStatement insertData = conn.prepareStatement("INSERT Courses " + 
						"(courseID, courseTitle, department) VALUES (" + courseID + ", " + 
						courseTitle + ", " + department + ")", 
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);		
				insertData.executeUpdate();
				System.out.println("Continue? Y/N");
				
			}
			
			System.out.println("To start entering Classes type Y. If not, type anything else.");
			while(input.next().equalsIgnoreCase("Y")) {
				int classCode = 0;
				int courseID = 0;
				int studentID = 0;
				int year = 0;
				String semester = "\"";
				String GPA = "\"";
				
				System.out.println("Enter classCode, courseID, studentID, year, semester, GPA. "
						+ "(In that order)");
				classCode += input.nextInt();
				courseID += input.nextInt();
				studentID += input.nextInt();
				year += input.nextInt();
				semester += input.next() + "\"";
				GPA += input.next() + "\"";

				PreparedStatement insertData = conn.prepareStatement("INSERT Classes " + 
						"(classCode, courseID, studentID, year, semester, GPA) VALUES (" + 
						classCode + ", " + courseID + ", " + studentID + ", " + year + ", " + 
						semester + ", " + GPA + ")",
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);		
				insertData.executeUpdate();
				System.out.println("Continue? Y/N");
				
			}
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void deleteFromTables(Connection conn) {
		try {
			
			System.out.println("Delete entry from Students by typing in Y. If not, type anything else.");
			while(input.next().equalsIgnoreCase("Y")) {
				int studentID = 0;
			
				System.out.println("Type in the entry's studentID to delete");
				studentID += input.nextInt();
			
				PreparedStatement deleteData = conn.prepareStatement("DELETE FROM Students WHERE "
						+ " studentID = " + studentID,
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);		
				deleteData.execute();
				System.out.println("Continue? Y/N");
				
			}
		
			System.out.println("Delete entry from Courses type Y. If not, type anything else.");
			while(input.next().equalsIgnoreCase("Y")) {
				int courseID = 0;
			
				System.out.println("Type in the entry's courseID to delete");
				courseID += input.nextInt();
			
				PreparedStatement deleteData = conn.prepareStatement("DELETE FROM Courses WHERE "
						+ " courseID = " + courseID,
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);		
				deleteData.execute();
				System.out.println("Continue? Y/N");
			
			}
			
			System.out.println("Delete entry from Classes type Y. If not, type anything else.");
			while(input.next().equalsIgnoreCase("Y")) {
				int classID = 0;
			
				System.out.println("Type in the entry's classID to delete");
				classID += input.nextInt();

				PreparedStatement deleteData = conn.prepareStatement("DELETE FROM Classes WHERE "
						+ " classCode = " + classID,
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);		
				deleteData.execute();
				System.out.println("Continue? Y/N");
				
			}
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public static void displayTables(Connection conn) {
		try {
			PreparedStatement getStudentsTable = conn.prepareStatement("SELECT * FROM Students");
			ResultSet studentsRS = getStudentsTable.executeQuery();
			StudentQuery.printTable("Students", studentsRS);
			
			PreparedStatement getCoursesTable = conn.prepareStatement("SELECT * FROM Courses");
			ResultSet coursesRS = getCoursesTable.executeQuery();
			StudentQuery.printTable("Courses", coursesRS);

			PreparedStatement getClassesTable = conn.prepareStatement("SELECT * FROM Classes");
			ResultSet classesRS = getClassesTable.executeQuery();
			StudentQuery.printTable("Classes", classesRS);
			
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void printTable(String tableName, ResultSet set) {
		try {
			ResultSetMetaData rsmd = set.getMetaData();
			int col = rsmd.getColumnCount();
			String columnString = "";
	   	    if(col > 0){
    	    	columnString = "\nTable: " + tableName + "\n" +
    	    			"=======================================================\n";
    	        for(int i = 1; i <= col; i++)
    	        	columnString += rsmd.getColumnLabel(i) + " ";
    	    }
    	    System.out.println(columnString);
    	    System.out.println("=======================================================");
    	    while(set.next()){
    	    	columnString = "";
    	    	for(int i = 1; i <= col; i++){
    	          String column = set.getString(i);
    	          if(column != null)
    	        	  columnString += column + " ";
    	        }
    	    	System.out.println(columnString + '\n' +
    	    			"------------------------------------------------------");
    	    }
			
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void displayPieChart(Connection conn) {
		try {
			Map<String, Integer> f = new TreeMap<String, Integer>();

			PreparedStatement findGPA = conn.prepareStatement("SELECT GPA FROM Classes "
					+ "WHERE courseID = 221 && year = 2019 && semester = \"Fall\"");
			ResultSet grabGPA = findGPA.executeQuery();
			String stringGPA = "";
			while(grabGPA.next()) {
				stringGPA = "";
				stringGPA = grabGPA.getString("GPA");
				StudentQuery.incrementFrequency(f, stringGPA);
				totalStudents++;
				
			}
			
			probabilitiesMapForPieChart = new TreeMap<String, String>();
			
			DecimalFormat dF = new DecimalFormat("#.####");
			dF.setRoundingMode(RoundingMode.CEILING);
			
			for(Map.Entry<String, Integer> entry : f.entrySet()){
				probabilitiesMapForPieChart.put(entry.getKey().toString(),
						dF.format(entry.getValue() / totalStudents));
				System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
			}
			System.out.println("TotalStudents " + totalStudents);
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void incrementFrequency(Map<String, Integer> f, String GPA){
		f.putIfAbsent(GPA, 0);
		f.put(GPA, f.get(GPA) + 1);
	}
	
    @Override
    public void start(Stage stage){
        PieChart p = new PieChart();
        Group group = p.draw(600, 600, probabilitiesMapForPieChart, totalStudents);
        Scene sc = new Scene(group, 600, 600);

        stage.setTitle("Pie Chart of Students GPA in CSC221");
        stage.setScene(sc);
        stage.show();
    }
	
}
