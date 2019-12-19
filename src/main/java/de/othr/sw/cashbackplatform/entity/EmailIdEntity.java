package de.othr.sw.cashbackplatform.entity;

import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@MappedSuperclass
public abstract class EmailIdEntity extends SingleIdEntity<String> {
	@Id
	@NotNull
	@Pattern(regexp="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
	private String email;

	public EmailIdEntity(String email) {
		super();
		this.email = email;
	}
	
	public EmailIdEntity() {}
	
	public String getId() {
		return this.email;
	}
	
	public void setId(String email) {
		this.email = email;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final EmailIdEntity other = (EmailIdEntity) o;
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
