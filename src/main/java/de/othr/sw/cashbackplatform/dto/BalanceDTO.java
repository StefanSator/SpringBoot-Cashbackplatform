package de.othr.sw.cashbackplatform.dto;

import java.util.Date;

public class BalanceDTO {
	private Date date;
	private String accountIdentification;
	private String surname;
	private String name;
	private Long currentCashbackPoints;
	
	public BalanceDTO() {}
	
	public BalanceDTO(Date date, String accountIdentification, String surname, String name, long currentPoints) {
		this.date = date;
		this.accountIdentification = accountIdentification;
		this.surname = surname;
		this.name = name;
		currentCashbackPoints = currentPoints;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getAccountIdentification() {
		return this.accountIdentification;
	}
	
	public void setAccountIdentification(String id) {
		this.accountIdentification = id;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getCashbackpoints() {
		return this.currentCashbackPoints;
	}
	
	public void setCashbackpoints(long cashbackpoints) {
		currentCashbackPoints = cashbackpoints;
	}
}
