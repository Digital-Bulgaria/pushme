package eu.balev.pushme.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Container {

	@Id
	private String id;

	public Container() {
		id = UUID.randomUUID().toString();
	}

	public Container(String id) {
		this.id = id;
	}

	public String getID()
	{
		return id;
	}
}
