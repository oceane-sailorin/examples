package recherche;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.*;


public class Autoscoot extends Search{


	protected static List<Map<String, String>> throughPage(WebDriver driver,List<Map<String, String>> data) {	
		
		
		int nb = data.size();
		List<WebElement> linkList=driver.findElements(By.tagName("a"));
		
		//now traverse over the list and check
		List<String> hrefs = new ArrayList<String>();
		for(int i=0 ; i<linkList.size() ; i++)
		{
			try {				
		        if (linkList.get(i).getAttribute("href") != null){
		        	if(linkList.get(i).getAttribute("href").contains("classified"))
				    {
		        			
		        		if (!hrefs.contains(linkList.get(i).getAttribute("href"))) 
		        		{
		        			//System.out.println(linkList.get(i).getAttribute("href"));
		        			hrefs.add(linkList.get(i).getAttribute("href"));
		        		}
				        //linkList.get(i).click();
				        //break;
				    }
		        }
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
			
			
		}
		
		System.out.println("afterfirstfor");	
		
		
		for (String href : hrefs) {
			try {
				
				System.out.println(href);	
				driver.navigate().to(href);	        
			    Thread.sleep(3000); 
			    Map<String, String> map = new HashMap<String, String>();
			    
			    String titre = driver.findElement(By.tagName("h1")).getText().toString();
			    System.out.println(titre);
			    map.put("titre", titre);
			    
			    String prix = driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[3]/div[1]/div[1]/div[2]/div/div[2]/div/div[1]")).getText().toString();
			    System.out.println(prix);
			    map.put("prix", prix);
			    
			    String km = driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[3]/div[1]/div[1]/div[2]/div/div[2]/div/div[4]")).getText().toString();
			    System.out.println(km);
			    map.put("km", km);
			    
			    String circulation = driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[3]/div[1]/div[1]/div[2]/div/div[2]/div/div[5]")).getText().toString();
			    System.out.println(circulation);
			    map.put("circulation", circulation);
			    
			    String carburant = driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[3]/div[1]/div[1]/div[2]/div/div[2]/div/div[7]")).getText().toString();
			    System.out.println(carburant);
			    map.put("carburant", carburant);
			    
			    try {		
				    WebElement container=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[3]/div[1]/div[1]/div[4]/div[2]"));
				   
				    List<WebElement> detailsLabels=container.findElements(By.className("labelWithBreaks"));
				    List<WebElement> details=container.findElements(By.className("propertyWithBreaks"));
				    for(int j=0 ; j<detailsLabels.size() ; j++)
					{
				    	System.out.print(detailsLabels.get(j).getText().toString());
				    	System.out.println(details.get(j).getText().toString());
				    	map.put(detailsLabels.get(j).getText().toString(), details.get(j).getText().toString());
					}
			    } catch (Exception e) {
			    	e.printStackTrace();
			    } 
			    
			    try {	
			    	
			    	String contact;
			    	//boolean isSeller = existsElementCss(driver,"div[data-test='sellerCompanyLink']");
					//if(isSeller){
						
			    	    List<WebElement> contactLink=driver.findElements(By.cssSelector("div[data-test='sellerCompanyLink']"));
					    if(contactLink.size()>0){					    					  
				    	    contact=contactLink.get(0).findElement(By.className("fontHeadline")).getText().toString();
						    System.out.print("Contact :");
						    System.out.println(contact);
					    }else{
							contact = "";
					    }
					//}else{
					//	contact = "";
					//}
					map.put("contact", contact);
				    
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			    
			    try {			
				    String contactTel=driver.findElement(By.cssSelector("span[data-test='phoneNumbers']")).getText().toString();
				    System.out.print("Contacttel :");
				    System.out.println(contactTel);
				    map.put("tel", contactTel);
				    
			    } catch (Exception e) {
			    	e.printStackTrace();
			    } 
			    
			    boolean accidentee = false;
			    try {			
				    accidentee = isTextPresent(driver,"ongeluk");
				    if(!accidentee) accidentee = isTextPresent(driver,"ongeval");
				    if(!accidentee) accidentee = isTextPresent(driver,"Nachgewiesene");
				    if(!accidentee) accidentee = isTextPresent(driver,"Unfall") && !isTextPresent(driver,"Unfallfrei");
				    //if(!accidentee) accidentee = isTextPresent(driver,"accident");
				    System.out.print("Accidentée :");
				    System.out.println(accidentee);
				    map.put("Accidentée", String.valueOf(accidentee));
				    
				    
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }  
			    
			    data.add(nb, map);
			    nb++;
				driver.navigate().back();
				//break;
			 } catch (Exception e) {
			    	e.printStackTrace();
			 }
		}
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
		try {
			
			WebElement next;  
			boolean isNext = existsElement(driver,"pagingBottomArrowRight");
			boolean isDisabled = isTextPresent(driver,"rightArrowClick disabled");
			if(isNext && !isDisabled ){
				next = driver.findElement(By.className("pagingBottomArrowRight"));
				next.click();
				data = callPage(driver,data);
			}	    
		 } catch (Exception e) {
		    	e.printStackTrace();
		 }
		return data;
	}		
	
	
	
	public static void main(Map<String,String> parameters,String[] args) {
		
		WebDriver driver = new FirefoxDriver();
		
		
		 for(String key: parameters.keySet()){
			System.out.println(key + " - " + parameters.get(key));
		 	System.out.println();
		 }
		 
		    String query_url = "SELECT * FROM mapping where site='autoscoot' and param='url' ";
	        String query = "SELECT * FROM mapping where site='autoscoot' ";
	        ResultSet rs_url = null;
	        Connection connection_url = null;
	        Statement statement_url = null; 
	        ResultSet rs = null;
	        Connection connection = null;
	        Statement statement = null; 
	        String label_search = null;
	        String name_search = null;
	        try {           
	        	connection_url = Db_connection.getConnection();
	            //statement = connection.createStatement();
	        	statement_url = connection_url.prepareStatement(query_url);  
	        	rs_url = statement_url.executeQuery(query_url);
	             
	            if (rs_url.next()) {
	            	System.out.println(rs_url.getString("matchval"));
	            	// Launch the Online Store Website
	        		driver.get(rs_url.getString("matchval"));

	        		// Print a Log In message to the screen
	        		System.out.println("Successfully opened the website");
	        			                        	
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (connection_url != null) {
	                try {
	                	connection_url.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        
	        try {           
	            connection = Db_connection.getConnection();
	            //statement = connection.createStatement();
	            statement = connection.prepareStatement(query);  
	            rs = statement.executeQuery(query);
	             
	            while (rs.next()) {
	            	System.out.println(rs.getInt("id"));
	            	System.out.println(rs.getString("param"));
	            	if (parameters.containsKey(rs.getString("param"))) {
	            		act(driver, rs.getString("matchval"), rs.getString("action"), rs.getString("element"), parameters.get(rs.getString("param")));
	            	}
	            	//type_model
	            	//WebElement radioBtn = driver.findElement(By.id("bodytypeCheckbox2"));
	            	//radioBtn.click();
	            	//brand
	            	//Select oSelect = new Select(driver.findElement(By.id("make0")));	 
	            	//oSelect.selectByVisibleText("BMW");
	            	//price
	        		////Select oSelect2 = new Select(driver.findElement(By.id("priceFrom")));	 
	        		//oSelect2.selectByValue("100000");
	        		
	            	//first_registration
	            	//Select oSelect3 = new Select(driver.findElement(By.id("firstRegistrationFrom")));	 
	            	//oSelect3.selectByValue("2016");
	        		
	        		System.out.println("Search");	
	        		if(rs.getString("param").equals("search_link"))
	        		{
	        			label_search = rs.getString("matchval");
	        		}
	        		
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (connection != null) {
	                try {
	                    connection.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }		 
		  
		
		
	    if(label_search != null && !label_search.isEmpty()) {
	         WebElement radioBtn = driver.findElement(By.id("searchLinkTop"));
	         radioBtn.click();
           
				System.out.println("beforewait");	
				try {
					// Wait for 5 Sec
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("after wait");	
				
				//list page
				List<Map<String, String>> data = new ArrayList<>();
				data = callPage(driver,data);
					
				//request.getRequestDispatcher("EspaceValidation.jsp").forward(request, response);
				if (parameters.containsKey("search_name")) {
					name_search = parameters.get("search_name");
				}
				if(name_search == null || name_search.isEmpty()) {
					name_search = "Manual";
				}
				
				for(int k=0 ; k<data.size() ; k++)
				{
					 try {
						 System.out.println(data.get(k).get("titre") );
						 System.out.println(data.get(k).get("prix") );
					 } catch (Exception e) {
					    	e.printStackTrace();
					 }	
				}
				if(insert_data(data,name_search,"autoscoot")){
					System.out.println("inserts ok");
				}else{
					System.out.println("pb inserts");
				}
	    }
		// Close the driver
		driver.quit();

	}

}

