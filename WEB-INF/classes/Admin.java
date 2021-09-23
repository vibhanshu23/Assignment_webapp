import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;

@WebServlet("/Admin")

public class Admin extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		System.out.println("PART 1 chalgyaya");
		System.out.println("action" + request.getParameter("Action"));
		System.out.println("list" + request.getParameter("list"));
		System.out.println("productName" + request.getParameter("productName"));

		if(request.getParameter("Action")!=null && request.getParameter("Action").equals("Delete")){
			if(request.getParameter("productName")!=null){
				if(request.getParameter("list")!=null && request.getParameter("list").equals("Console")){
				
					System.out.println("chalgyaya");
					String consoleToRemove = null;
					for(Map.Entry<String,Console> entry : SaxParserDataStore.consoles.entrySet()){
						System.out.println("hashProdName ." + entry.getValue().getId() + ".   to match name ." +request.getParameter("productName"));
						if(entry.getValue().getId().equals(request.getParameter("productName"))){
							consoleToRemove = entry.getKey();
							break;
						}
					}
					System.out.println("KEY is " + consoleToRemove);
					if(!consoleToRemove.equals(null)){
						SaxParserDataStore.consoles.remove(consoleToRemove);
					}
				}
				else if(request.getParameter("list")!=null && request.getParameter("list").equals("Games")){
					String consoleToRemove = null;
		
					for(Map.Entry<String,Game> entry : SaxParserDataStore.games.entrySet()){
						if(entry.getValue().getId().equals(request.getParameter("productName"))){
							consoleToRemove = entry.getKey();
							break;
						}
					}
					if(!consoleToRemove.equals(null)){
						SaxParserDataStore.games.remove(consoleToRemove);
					}
				}
				else if(request.getParameter("list")!=null && request.getParameter("list").equals("Tablets")){
					String consoleToRemove = null;
		
					for(Map.Entry<String,Tablet> entry : SaxParserDataStore.tablets.entrySet()){
						if(entry.getValue().getId().equals(request.getParameter("productName"))){
							consoleToRemove = entry.getKey();
							break;
						}
					}
					if(!consoleToRemove.equals(null)){
						SaxParserDataStore.tablets.remove(consoleToRemove);
					}
				}
				else if(request.getParameter("list")!=null && request.getParameter("list").equals("accessories")){
					String consoleToRemove = null;
		
					for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessories.entrySet()){
						if(entry.getValue().getId().equals(request.getParameter("productName"))){
							consoleToRemove = entry.getKey();
							break;
						}
					}
					if(!consoleToRemove.equals(null)){
						SaxParserDataStore.accessories.remove(consoleToRemove);
					}
				}
				funcStoreCurentHashmaptoDisk();
			}
		}
		else if(request.getParameter("Action")!=null && request.getParameter("Action").equals("Add")){
			
			Console newConsoleToAdd = new Console();
			// newConsoleToAdd.setImage();
			newConsoleToAdd.setDiscount(Double.parseDouble(request.getParameter("setDiscount")));
			newConsoleToAdd.setCondition(request.getParameter("setCondition"));
			newConsoleToAdd.setRetailer(request.getParameter("setRetailer"));
			newConsoleToAdd.setName(request.getParameter("setName"));
			newConsoleToAdd.setId(request.getParameter("setId"));
			newConsoleToAdd.setPrice(Double.parseDouble(request.getParameter("setPrice")));

			SaxParserDataStore.consoles.put(newConsoleToAdd.getId(), newConsoleToAdd);
			funcStoreCurentHashmaptoDisk();

		}


		displayAdminPage(request, response);
	}

	/* Display Account Details of the Customer (Name and Usertype) */

	protected void displayAdminPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		User user = utility.getUser();
		try
         {  
           response.setContentType("text/html");
			HttpSession session=request.getSession(); 	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			int size=0;
			for(Map.Entry<String,Console> entry : SaxParserDataStore.consoles.entrySet())
			 {
					size= size+1;
					// System.out.println("---- variable---" + entry.getValue().getDiscount());
					// System.out.println("---- variable---" + entry.getValue().getCondition());
					// System.out.println("---- variable---" + entry.getValue().getRetailer());
					// System.out.println("---- variable---" + entry.getValue().getName());
					// System.out.println("---- variable---" + entry.getValue().getId());
					// System.out.println("---- variable---" + entry.getValue().getPrice());
					// break;
			 }	
				
			if(size>0)
				{	
					pw.print("<table class='gridtable'>");

					pw.print("<form method='get' action='Admin'>");
										
					pw.print("<tr><td></td></tr>");		
					pw.print("<tr>");
					pw.print("<td> Select to Remove </td>");
					pw.print("<td> Product Name </td>");
					pw.print("<td> List </td>");
					pw.print("</tr>");		
	

					for(Map.Entry<String,Console> entry : SaxParserDataStore.consoles.entrySet())
					{
							pw.print("<tr>");			
							pw.print("<td><input type='radio' name='productName' value='"+entry.getValue().getId()+"'></td>");			
							
							pw.print("<td>"+entry.getValue().getName()+".</td><td>"+entry.getValue().getRetailer()+"</td>");
							pw.print("<td><input type='hidden' name='list' value='Console'></td>");
							pw.print("<td><input type='submit' name='Action' value='Delete' class='btnbuy'></td>");
							pw.print("</tr>");
							pw.print("</form>");
					}
					for(Map.Entry<String,Game> entry : SaxParserDataStore.games.entrySet())
					{
							pw.print("<tr>");			
							pw.print("<td><input type='radio' name='productName' value='"+entry.getValue().getId()+"'></td>");			
							
							pw.print("<td>"+entry.getValue().getName()+".</td><td>"+entry.getValue().getRetailer()+"</td>");
							pw.print("<td><input type='hidden' name='list' value='Games'></td>");
							pw.print("<td><input type='submit' name='Action' value='Delete' class='btnbuy'></td>");
							pw.print("</tr>");
							pw.print("</form>");
					}
					for(Map.Entry<String,Tablet> entry : SaxParserDataStore.tablets.entrySet())
					{
							pw.print("<tr>");			
							pw.print("<td><input type='radio' name='productName' value='"+entry.getValue().getId()+"'></td>");			
							
							pw.print("<td>"+entry.getValue().getName()+".</td><td>"+entry.getValue().getRetailer()+"</td>");
							pw.print("<td><input type='hidden' name='list' value='Tablets'></td>");
							pw.print("<td><input type='submit' name='Action' value='Delete' class='btnbuy'></td>");
							pw.print("</tr>");
							pw.print("</form>");
					}
					for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessories.entrySet())
					{
							pw.print("<tr>");			
							pw.print("<td><input type='radio' name='productName' value='"+entry.getValue().getId()+"'></td>");			
							
							pw.print("<td>"+entry.getValue().getName()+".</td><td>"+entry.getValue().getRetailer()+"</td>");
							pw.print("<td><input type='hidden' name='list' value='accessories'></td>");
							pw.print("<td><input type='submit' name='Action' value='Delete' class='btnbuy'></td>");
							pw.print("</tr>");
							pw.print("</form>");
					}

					// pw.print("<tr><td>");
					// pw.print("Name</td>");
					// pw.print("<td><input type='text' name='setName'>");
					// pw.print("</td></tr>");
					
					// pw.print("<tr><td>");
					// pw.print("Discount</td>");
					// pw.print("<td><input type='text' name='setDiscount'>");
					// pw.print("</td></tr>");

					// pw.print("<tr><td>");
					// pw.print("Condition</td>");
					// pw.print("<td><input type='text' name='setCondition'>");
					// pw.print("</td></tr>");
					
					// pw.print("<tr><td>");
					// pw.print("Manufacturer</td>");
					// pw.print("<td><input type='text' name='setRetailer'>");
					// pw.print("</td></tr>");

					// pw.print("<tr><td>");
					// pw.print("ID</td>");
					// pw.print("<td><input type='text' name='setId'>");
					// pw.print("</td></tr>");

					// pw.print("<tr><td>");
					// pw.print("Price</td>");
					// pw.print("<td><input type='text' name='setPrice'>");
					// pw.print("</td></tr>");
					// pw.print("<tr><input type='submit' name='Action' value='Add' class='btnbuy'></tr>");
					// pw.print("</form>");




					pw.print("</table>");
				}
				else
				{
					pw.print("<h4 style='color:red'>You have not placed any order with this order id</h4>");
				}
			
			pw.print("</table>");		
			pw.print("</h2></div></div></div>");	
			utility.printHtml("Footer.html");	        	
		}
		catch(Exception e)
		{
		}		
	}

	private void funcStoreCurentHashmaptoDisk() {
		String TOMCAT_HOME = System.getProperty("catalina.home");

		funcStoreProductHashmapToDisk(SaxParserDataStore.consoles, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapA.txt"));
		funcStoreProductHashmapToDisk(SaxParserDataStore.games, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapB.txt"));
		funcStoreProductHashmapToDisk(SaxParserDataStore.tablets, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapC.txt"));
		funcStoreProductHashmapToDisk(SaxParserDataStore.accessories, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapD.txt"));
		// funcStoreProductHashmapToDisk(SaxParserDataStore.accessoryHashMap, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapE.txt"));
		// funcStoreProductHashmapToDisk(VOICEASSISTANT, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapF.txt"));
	}

	private void funcStoreProductHashmapToDisk(HashMap hm,String path) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(hm);
			objectOutputStream.flush();
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception e) {
			System.out.println("exceptionnnnnnnn SaxParser storing hashmap" + hm + "at path" + path);
		}

	}
}
