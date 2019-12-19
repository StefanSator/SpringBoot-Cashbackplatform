package de.othr.sw.cashbackplatform.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SingleIdEntity<T> {
	
	public SingleIdEntity() {};
	
	public abstract T getId();
	public abstract int hashCode();
	public abstract boolean equals(Object object);
	
}
