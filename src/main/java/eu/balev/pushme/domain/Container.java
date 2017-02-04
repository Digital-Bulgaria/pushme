package eu.balev.pushme.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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
	
	public Date getDateCreated() {
		return dateCreated != null ? new Date(dateCreated.getTime()) : null;
	}
	
	public void setDateCreated(Date date) {
		this.dateCreated = date != null ? new Date(date.getTime()) : null;
	}
	
	@PrePersist
	void createdAt() {
		if (dateCreated == null) {
			this.dateCreated = new Date();
		}
	}

}