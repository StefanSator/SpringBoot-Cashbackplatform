package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
public class Cashback extends GeneratedIdEntity implements Serializable {
	
	private static final long serialVersionUID = 2269339129729135590L;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;
	@NotNull
	@OneToMany
	private List<Cashbackposition> cashbackpositions;
	@NotNull
	@ManyToOne
	private PrivateCustomer receiver;
	@NotNull
	@ManyToOne
	private Shop sender;
	
	public Cashback() { super(); };
	
	public Cashback(Date date, List<Cashbackposition> cashbackpositions, PrivateCustomer receiver, Shop sender) {
		super();
		this.date = date;
		this.cashbackpositions = cashbackpositions;
		this.receiver = receiver;
		this.sender = sender;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public PrivateCustomer getReceiver() {
		return this.receiver;
	}
	
	public void setReceiver(PrivateCustomer receiver) {
		this.receiver = receiver;
	}
	
	public Shop getSender() {
		return this.sender;
	}
	
	public void setSender(Shop sender) {
		this.sender = sender;
	}
	
	public List<Cashbackposition> getCashbackpositions() {
		return this.cashbackpositions;
	}
	
	public void setCashbackpositions(List<Cashbackposition> cashbackpositions) {
		this.cashbackpositions = cashbackpositions;
	}
	
	public Cashbackposition getCashbackposition(int index) {
		return this.cashbackpositions.get(index);
	}
	
	public void setCashbackposition(int index, Cashbackposition cashbackposition) {
		this.cashbackpositions.set(index, cashbackposition);
	}
	
	public Cashbackposition removeCashbackposition(int index) {
		return this.cashbackpositions.remove(index);
	}
	
	public void addCashbackposition(Cashbackposition cashbackposition) {
		this.cashbackpositions.add(cashbackposition);
	}
	
}
