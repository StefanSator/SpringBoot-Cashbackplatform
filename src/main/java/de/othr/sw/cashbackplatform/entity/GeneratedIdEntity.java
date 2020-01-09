package de.othr.sw.cashbackplatform.entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GeneratedIdEntity extends SingleIdEntity<Long> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	public GeneratedIdEntity() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final GeneratedIdEntity other = (GeneratedIdEntity) o;
		if (!Objects.equals(this.id, other.id)) return false;
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
