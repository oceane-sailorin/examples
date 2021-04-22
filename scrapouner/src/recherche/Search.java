package recherche;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Search {

	protected static boolean isTextPresent(WebDriver driver,String text){
	    try{
	        boolean b = driver.getPageSource().contains(text);
	        return b;
	    }
	    catch(Exception e){
	        return false;
	    }
	  }
	
	protected static boolean existsElement(WebDriver driver,String classe) {	
		
		try {
	        driver.findElement(By.className(classe));
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	    return true;
	}
	
	protected static boolean existsElementCss(WebDriver driver,String selector) {	
		
		try {
	        driver.findElement(By.cssSelector(selector));
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	    return true;
	}
	
	protected static List<Map<String, String>> throughPage(WebDriver driver,List<Map<String, String>> data) {		
		
		return data;
	}

	public static List<Map<String, String>> callPage(WebDriver driver,List<Map<String, String>> data){
		
		System.out.println("callpage");	
		try {
			// Wait for 5 Sec
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		data = throughPage(driver,data);
		
		return data;
	}		
	
	public static void act(WebDriver driver,String matchval,String action, String element, String value){
		WebElement btn;
		Select oSelect;
        switch (action) {
            case "click":  
            	switch (element) {
            		case "id":
            			btn = driver.findElement(By.id(matchval));
            			break;
            		default:
            			btn = driver.findElement(By.id(matchval));
            			break;
            	}
            	btn.click();	
                break;
            case "selectByVisibleText":  
            	switch (element) {
            		case "id":
            			oSelect = new Select(driver.findElement(By.id(matchval)));	
            			break;
            		default:
            			oSelect = new Select(driver.findElement(By.id(matchval)));	
            			break;
            	}
            	oSelect.selectByVisibleText(value);
                break;
            case "selectByValue":  
            	switch (element) {
            		case "id":
            			oSelect = new Select(driver.findElement(By.id(matchval)));	
            			break;
            		default:
            			oSelect = new Select(driver.findElement(By.id(matchval)));	
            			break;
            	}
            	oSelect.selectByValue(value);
                break;                
        }
	}
	
	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
	
	public static boolean insert_data(List<Map<String, String>> data, String name_search, String site){
		
	        String query = "insert into search_vehicle (site, vehicle, search_name, search_date, search_time) values( ?, ?, ?, ?, ?) ";
	        String query_param = "insert into search_param (search_vehicle, param, matchval) values( ?, ?, ?) ";
	        ResultSet rs = null;
	        Connection connection = null;
	        PreparedStatement statement = null; 
	        PreparedStatement statement_param = null; 
	        int i = 0;
	        boolean execok = true;
	        java.sql.Date startDate = java.sql.Date.valueOf(java.time.LocalDate.now());
	    	
	        int last_inserted_id = 0;

	        try {           
	            connection = Db_connection.getConnection();
	            for(int k=0 ; k<data.size() ; k++)
				{
		            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);  
		            statement.setString(1,site);
		            statement.setString(2, data.get(k).get("titre"));  	     
		            statement.setString(3, name_search);  
		            statement.setDate(4,startDate);  
		            statement.setTimestamp(5,getCurrentTimeStamp());  
		            i = statement.executeUpdate();                   
		            System.out.println(i+" records inserted");  
		            rs = statement.getGeneratedKeys();
	                if(rs.next())
	                {
	                    last_inserted_id = rs.getInt(1);
	                }
	                //data.get(k).forEach((key, value) -> {
	                for (String key : data.get(k).keySet()) {
	                	try {                		
		                	statement_param = connection.prepareStatement(query_param);  
			                statement_param.setInt(1,last_inserted_id);
			                statement_param.setString(2, key);  	     
			                statement_param.setString(3, data.get(k).get(key));  
				            statement_param.executeUpdate();   
	                	} catch (SQLException e) {
	        	            e.printStackTrace();          
	        	        } 
	                }
		            
				}
	            
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            execok = false;
	        } finally {
	            if (connection != null) {
	                try {
	                    connection.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        return execok;
	            
	}	
	
	public static void main(String[] args) {
		
		WebDriver driver = new FirefoxDriver();
		
		//list page		
		List<Map<String, String>> data = new ArrayList<>();
		data = callPage(driver,data);
			
		//request.getRequestDispatcher("EspaceValidation.jsp").forward(request, response);
	
		
		for(int k=0 ; k<data.size() ; k++)
		{
			 try {
				 System.out.println(data.get(k).get("titre") );
				 System.out.println(data.get(k).get("prix") );
			 } catch (Exception e) {
			    	e.printStackTrace();
			 }	
		}

	}

}
