package eu.balev.pushme.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Container {

	@Id
	private String id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "container_id")
	private List<Request> requests;

	public Container() {
		id = UUID.randomUUID().toString();
	}

	public Container(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	
	public int getRequestsCount()
	{
		return requests != null ? requests.size() : 0;
	}
}