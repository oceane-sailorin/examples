package resultat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Sel_connection {


	 

	    //static reference to itself
	    private static Sel_connection instance = new Sel_connection();
	    public static final String URL = "jdbc:mysql://localhost/scrapouner";
	    public static final String USER = "xxxxxx";
	    public static final String PASSWORD = "xxxxxxxx";
	    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
	     
	    //private constructor
	    private Sel_connection() {
	        try {
	            Class.forName(DRIVER_CLASS);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	     
	    private Connection createConnection() {
	 
	        Connection connection = null;  
	            
	            try {
	               
	            	connection = DriverManager.getConnection(URL, USER, PASSWORD);
	                System.out.println("Database connection established");
	            }catch(Exception e){
	            	System.out.println("not good " + e.getMessage());
	            }
	        return connection;
	    }   
	     
	    public static Connection getConnection() {
	        return instance.createConnection();
	    }
	}

