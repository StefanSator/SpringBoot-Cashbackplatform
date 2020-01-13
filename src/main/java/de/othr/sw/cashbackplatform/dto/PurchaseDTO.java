package de.othr.sw.cashbackplatform.dto;

import java.util.Date;
import java.util.Map;

public class PurchaseDTO {
	private Date date;
	private String purchaseIdentification;
	private String customerAccountIdentification;
	private String customerPassword;
	private String shopEmail;
	private String shopPassword;
	private Map<String, Double> prices;
	private Double totalPrice;
	
	public PurchaseDTO() {}
	
	public PurchaseDTO(Date date, String purchaseIdentification, String customerAccountIdentification, String customerPassword,
					   String shopEmail, String shopPassword, Map<String, Double> prices, Double totalPrice) {
		this.date = date;
		this.purchaseIdentification = purchaseIdentification;
		this.customerAccountIdentification = customerAccountIdentification;
		this.customerPassword = customerPassword;
		this.shopEmail = shopEmail;
		this.shopPassword = shopPassword;
		this.prices = prices;
		this.totalPrice = totalPrice;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getPurchaseIdentification() {
		return this.purchaseIdentification;
	}
	
	public void setPurchaseIdentification(String purchaseid) {
		purchaseIdentification = purchaseid;
	}
	
	public String getCustomerAccountIdentification() {
		return this.customerAccountIdentification;
	}
	
	public void setCustomerAccountNumber(String customerAccountNumber) {
		this.customerAccountIdentification = customerAccountNumber;
	}
	
	public String getCustomerPassword() {
		return this.customerPassword;
	}
	
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	
	public String getShopEmail() {
		return this.shopEmail;
	}
	
	public void setShopEmail(String email) {
		this.shopEmail = email;
	}
	
	public String getShopPassword() {
		return this.shopPassword;
	}
	
	public void setShopPassword(String password) {
		this.shopPassword = password;
	}
	
	public Map<String, Double> getPrices() {
		return this.prices;
	}
	
	public void setPrices(Map<String, Double> prices) {
		this.prices = prices;
	}
	
	public Double getTotalPrice() {
		return this.totalPrice;
	}
	
	public void setTotalPrice(Double totalprice) {
		totalPrice = totalprice;
	}
}
