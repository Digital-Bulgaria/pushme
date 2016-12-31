package eu.balev.pushme.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Container {

	@Id
	private String id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "container_id")
	@OrderBy("dateCreated DESC")
	private List<Request> requests;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "container_id")
	@OrderBy("sortOrder")
	private List<Rule> rules;
	
	public Container() {
		id = UUID.randomUUID().toString();
	}

	public Container(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	//requests
	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public int getRequestsCount() {
		return requests != null ? requests.size() : 0;
	}
	
	//rules
	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public int getRulesCount() {
		return rules != null ? rules.size() : 0;
	}
}