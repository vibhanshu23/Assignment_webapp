import java.io.IOException;
import java.io.PrintWriter;
// import java.sql.Date;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.text.*;
@WebServlet("/PaymentNew")

public class PaymentNew extends HttpServlet {
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to Pay");
			response.sendRedirect("Login");
			return;
		}
		// get the payment details like credit card no address processed from cart servlet	

		String userAddress=request.getParameter("userAddress");
		String type=request.getParameter("where");
		String creditCardNo=request.getParameter("creditCardNo");
		String deliveryType =request.getParameter("type"); //
		System.out.println("type --"+type);
		String ChoseCustomer =request.getParameter("ChoseCustomer"); //
		

		Date orderDate = new Date();  
		int zipCode = 60606;
		if(!userAddress.isEmpty() && !creditCardNo.isEmpty() && !deliveryType.isEmpty() && !deliveryType.equals("Home Delivery") && type==null){
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
		
			
			pw.print("<form action='PaymentNew' method='post' name='where' value='zip'>");
			pw.print("<input type='hidden' name='userAddress' value='"+userAddress+"'><input type='hidden' name='type' value='"+"Home Delivery"+"'><input type='hidden' name='creditCardNo' value='"+creditCardNo+"'>");

			pw.print("<table><thead><tr><th></th></tr></thead><tbody><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr><tr><td>"+ ( zipCode++) +"</td><td><input type='submit' class='btnbuy' value='Pick at "+ ( zipCode++) +"'></td></tr></tbody></table>");
			pw.print("</form>");
			System.out.println("Payment.java the user address is" +userAddress + "---------------" +"and type is " +deliveryType);
			System.out.println("cc --"+creditCardNo);

			pw.print("</h2></div></div></div>");		
			utility.printHtml("Footer.html");

		}else if(!userAddress.isEmpty() && !creditCardNo.isEmpty() && !deliveryType.isEmpty()) //check for mandate
		{
			int orderId=utility.getOrderPaymentSize()+1;
			System.out.println("going for order " +creditCardNo);
			//iterate through each order

			for (OrderItem oi : utility.getCustomerOrders())
			{

				//set the parameter for each column and execute the prepared statement
				System.out.println("Payment.java the user name is" +oi.getName() + "---------------");
				User user = utility.getUser();
				if(user.getUsertype().equals("salesman") && ChoseCustomer != null){

					utility.storePaymentwithUser(orderId,oi.getName(),oi.getPrice(),userAddress,creditCardNo,orderDate , deliveryType, ChoseCustomer); //check

				}
				else{
					utility.storePayment(orderId,oi.getName(),oi.getPrice(),userAddress,creditCardNo,orderDate , deliveryType); //check
				}
			}

			//remove the order details from cart after processing
			
			OrdersHashMap.orders.remove(utility.username());	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
		
			pw.print("<h2>Your Order");
			pw.print("&nbsp&nbsp");  
			pw.print("is stored ");

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			
			System.out.println(formatter.format(orderDate));  
			Calendar c = Calendar.getInstance();
			c.setTime(orderDate); 
			c.add(Calendar.DATE, 14); // Adding 5 days

			pw.print("<br>Your Order No is "+(orderId));//+ "and will be delivered by " + formatter.format(c.getTime()));
			pw.print("</h2></div></div></div>");		
			utility.printHtml("Footer.html");
		}else
		{
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
		
			pw.print("<h4 style='color:red'>Please enter valid address and creditcard number</h4>");
			pw.print("</h2></div></div></div>");		
			utility.printHtml("Footer.html");
		}	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		
	}
}
