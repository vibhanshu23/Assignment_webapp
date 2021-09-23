import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Cart")

public class Cart extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String warranty = request.getParameter("AskWarraanty");
		System.out.println("warranty" + warranty);
		Utilities utility = new Utilities(request, pw);

		try {
			System.out.println(" VALUE OF ORDER TO BE REMOVED OR NULL  " + request.getParameter("Order"));

			if(request.getParameter("Order")!=null && request.getParameter("Order").equals("removeItem")){
				String name = request.getParameter("orderName");
				String type = request.getParameter("type");
				String maker = request.getParameter("Retailer");
				String access = request.getParameter("access");
				System.out.println(" VALUE OF ORDER TO BE REMOVED OR NULL  " + request.getParameter("Order") + name+type+maker+access);

				utility.removeProductFromCart(name, type, maker, access);

				displayCart(request, response);

				return;
			}
	
	
			if((warranty != null && warranty.equals("NO"))){
				
				displayCart(request, response);
				return;
			}
			if((warranty == null || !warranty.equals("YES"))){
				String name = request.getParameter("name");
				String type = request.getParameter("type");
				String maker = request.getParameter("maker");
				String access = request.getParameter("access");
				System.out.println("warranty" + warranty);
				System.out.println("maker" + maker);
				System.out.println("name" + name);



				System.out.println("CART added name   " + name + "   type   " + type + "   maker   " + maker + "   accesee   " + access);

				/* StoreProduct Function stores the Purchased product in Orders HashMap.*/	
				System.out.println("CART added name   " + name + "   type   " + type + "   maker   " + maker + "   accesee   " + access);

			utility.storeProduct(name, type, maker, access);

			displayWarranty(request, response);
				return;
			}
			
		} catch (Exception e) {
			System.out.println("CART exception"+e.getMessage() + "--------" +e.getLocalizedMessage());
			//TODO: handle exception
		}
				String name = request.getParameter("name");
				String type = request.getParameter("type");
				String maker = request.getParameter("maker");
				String access = request.getParameter("access");
				System.out.println("warranty" + warranty);
				System.out.println("maker" + maker);
				System.out.println("name" + name);



				System.out.println("CART added name   " + name + "   type   " + type + "   maker   " + maker + "   accesee   " + access);

				/* StoreProduct Function stores the Purchased product in Orders HashMap.*/	

			utility.storeProduct(name, type, maker, access);
			System.out.println("CART added name   " + name + "   type   " + type + "   maker   " + maker + "   accesee   " + access);

		displayCart(request, response);
		
			/* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/

			
		
		
	}

	protected void displayWarranty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Cart("+utility.CartCount()+")</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<form name ='warranty' action='Cart' method='post'>");
		pw.print("<p><b>'Do you want to add 1 year warranty for your'");
		pw.print(request.getParameter("name") +"'</b></p><br />");

		pw.print("<form method='post' action='Cart'>");
		pw.print("<tr>");			
		pw.print("<td><input type='submit' value='NO' class='btnbuy'></td>");
		pw.print("<input type='hidden' name='AskWarraanty' value='NO'>");
		pw.print("</tr>");
		pw.print("</form>");

		pw.print("<form method='post' action='Cart'>");
		pw.print("<tr>");			
		pw.print("<td><input type='submit' value='YES' class='btnbuy'></td>");
		pw.print("<input type='hidden' name='AskWarraanty' value='YES'><input type='hidden' name='maker' value='Warranty'><input type='hidden' name='name' value='Warranty'><input type='hidden' name='type' value='consoles'>");
		pw.print("</tr>");
		pw.print("</form>");


		pw.print("</form>");
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");

	}

	

/* displayCart Function shows the products that users has bought, these products will be displayed with Total Amount.*/

	protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);
		Carousel carousel = new Carousel();
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Cart("+utility.CartCount()+")</a>");
		pw.print("</h2><div class='entry'>");
		if(utility.CartCount()>0)
		{
			pw.print("<table  class='gridtable'>");
			int i = 1;
			double total = 0;
			for (OrderItem oi : utility.getCustomerOrders()) 
			{
				pw.print("<form name ='Cart' action='Cart' method='post'>");

				pw.print("<tr>");
				pw.print("<td>"+i+".</td><td>"+oi.getName()+"</td><td>: "+oi.getPrice()+"</td>");
				pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
				pw.print("<input type='hidden' name='Retailer' value='"+oi.getRetailer()+"'>");
				pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
				pw.print("<input type='hidden' name='name' value='"+oi.getName()+"'>");
				// <input type='hidden' name='AskWarraanty' value='YES'><input type='hidden' name='name' value='Warranty'><input type='hidden' name='type' value='consoles'>
				pw.print("<td><input type='submit' name='Order' value='removeItem' class='btnbuy'></td>");
				pw.print("</tr>");
				pw.print("</form>");
				total = total +oi.getPrice();
				i++;

			}
			pw.print("<form name ='Cart' action='CheckOut' method='post'>");

			pw.print("<input type='hidden' name='orderTotal' value='"+total+"'>");	
			pw.print("<tr><th></th><th>Total</th><th>"+total+"</th>");
			pw.print("<tr><td></td><td></td><td><input type='submit' name='CheckOut' value='CheckOut' class='btnbuy' /></td>");
			pw.print("</table></form>");
			/* This code is calling Carousel.java code to implement carousel feature*/
			pw.print(carousel.carouselfeature(utility));
		}
		else
		{
			pw.print("<h4 style='color:red'>Your Cart is empty</h4>");
		}
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//on cart click
		System.out.println("CART BUTTON CLICKED");
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		displayCart(request, response);


	}
}
