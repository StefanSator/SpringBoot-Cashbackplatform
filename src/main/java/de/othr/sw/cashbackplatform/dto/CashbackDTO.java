package de.othr.sw.cashbackplatform.dto;

public class CashbackDTO {
	private String accountIdentification;
	private Integer cashbackpoints;
	private boolean isPositive;
	
	public CashbackDTO() {}
	
	public CashbackDTO(String accountIdentification, Integer cashbackpoints, boolean isPositive) {
		this.accountIdentification = accountIdentification;
		this.cashbackpoints = cashbackpoints;
		this.isPositive = isPositive;
	}
	
	public void setAccountIdentification(String id) {
		this.accountIdentification = id;
	}
	
	public String getAccountIdentification() {
		return this.accountIdentification;
	}
	
	public void setCashbackpoints(int points) {
		this.cashbackpoints = points;
	}
	
	public Integer getCashbackpoints() {
		return this.cashbackpoints;
	}
	
	public void setPositive(boolean value) {
		isPositive = value;
	}
	
	public boolean isPositive() {
		return this.isPositive;
	}
}
