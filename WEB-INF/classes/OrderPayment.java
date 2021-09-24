import java.io.IOException;
import java.util.Date;
import java.io.*;
import java.text.*;
import java.util.*;

/* 
	OrderPayment class contains class variables username,ordername,price,image,address,creditcardno.

	OrderPayment  class has a constructor with Arguments username,ordername,price,image,address,creditcardno
	  
	OrderPayment  class contains getters and setters for username,ordername,price,image,address,creditcardno
*/

public class OrderPayment implements Serializable{
	private int orderId;
	private String userName;
	private String orderName;
	private double orderPrice;
	private String userAddress;
	private String creditCardNo;
	private Date dateOrder;
	private String deliveryType;
	
	public OrderPayment(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo , Date dateOrder, String deliveryType){
		this.orderId=orderId;
		this.userName=userName;
		this.orderName=orderName;
	 	this.orderPrice=orderPrice;
		this.userAddress=userAddress;
		this.dateOrder=dateOrder;
		this.creditCardNo=creditCardNo;
		this.deliveryType=deliveryType;
		 
		}

	public String getStringDateOrder() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		Calendar c = Calendar.getInstance();
			c.setTime(dateOrder); 
			c.add(Calendar.DATE, 14); // Adding 5 days
			return formatter.format(c.getTime());
	}
	public Date getDateOrder() {
		return dateOrder;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setDateOrder(Date adateOrder) {
		this.dateOrder = adateOrder;
	}
	public void setDeliveryType(String adeliveryType) {
		this.deliveryType = adeliveryType;
	}	
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	

}
