package recherche;

import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import recherche.Autoscoot;


/**
 * Servlet implementation class MainSearch
 */
@WebServlet("/MainSearch")
public class MainSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().println("New search");
		response.getWriter().println(request.getParameter("year"));
		//RequestDispatcher rd = request.getRequestDispatcher("/recherche/Autoscoot");
		//rd.include(request, response);
		try{  
			int nb = 0;
			int redir = 0;
			String[] requests = new String[20];
			 Map<String,String> parameters = new HashMap<String,String>();
			 		 	    
			Enumeration<String> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
			 String paramName = parameterNames.nextElement();
			 response.getWriter().println(paramName);
			 String[] paramValues = request.getParameterValues(paramName);
			 requests[nb] = paramName;
			 nb++;
			  for (int i = 0; i < paramValues.length; i++) {
			       String paramValue = paramValues[i];
			       //response.getWriter().println(paramValue);
			       if(paramValue.equals("autoscoot")){
			    	   redir = 1;
			       }
			       parameters.put(paramName, paramValue);
			  }
			
		   }
	        if(redir == 1) {
	        	Autoscoot.main(parameters,requests);
	        }
	        	   
	                      
	    }catch(Exception e){
	        // System.out.println(e);
	    	response.getWriter().println(request.getParameter("No autoscoot"));
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
