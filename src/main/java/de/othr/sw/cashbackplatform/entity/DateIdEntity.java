package de.othr.sw.cashbackplatform.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class DateIdEntity {
	@Id
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;
	
	public DateIdEntity(Date date) {
		super();
		this.date = date;
	}
	
	public DateIdEntity() {}
	
	public Date getId() {
		return this.date;
	}
	
	public void setId(Date date) {
		this.date = date;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final DateIdEntity other = (DateIdEntity) o;
		if (!Objects.equals(this.getId(), other.getId())) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		if (this.getId() == null) {
			return 0;
		} else {
			return this.getId().hashCode();
		}
	}
}
