package recherche;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_connection {


	 

	    //static reference to itself
	    private static Db_connection instance = new Db_connection();
	    public static final String URL = "jdbc:mysql://localhost/scrapouner";
	    public static final String USER = "xxxxxx";
	    public static final String PASSWORD = "xxxxxxxx";
	    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
	     
	    //private constructor
	    private Db_connection() {
	        try {
	            Class.forName(DRIVER_CLASS);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	     
	    private Connection createConnection() {
	 
	        Connection connection = null;
	        //try {
	           // connection = DriverManager.getConnection(URL, USER, PASSWORD);
	       // } catch (SQLException e) {
	            //     System.out.println("ERROR: Unable to Connect to Database."+
	            //        ((SQLException)e).getErrorCode());
	            //    System.err.println("Message: " + e.getMessage());
	            //}
	            
	            try {
	               
	            	connection = DriverManager.getConnection(URL, USER, PASSWORD);
	                System.out.println("Database connection established");
	            }catch(Exception e){
	            	System.out.println("not good" + e.getMessage());
	            }
	        return connection;
	    }   
	     
	    public static Connection getConnection() {
	        return instance.createConnection();
	    }
	}
