package eu.balev.pushme.domain;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Container {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Container.class);
	
	@Id
	private String id;
	
	private String name;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "container_id")
	@OrderBy("dateCreated DESC")
	private List<Request> requests;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "container_id")
	@OrderBy("sortOrder")
	private List<Rule> rules;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	public Container() {
		id = UUID.randomUUID().toString();
		name = id;
	}
	
	public Container(String id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addRequest(Request request)
	{
		request.setContainer(this);
		if (requests == null)
		{
			requests = new LinkedList<>();
		}
		requests.add(0, request);
	}
	
	// requests
	public List<Request> getRequests() {
		return requests == null ? Collections.emptyList() : requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public int getRequestsCount() {
		return requests != null ? requests.size() : 0;
	}
	
	@JsonIgnore
	public boolean pruneRequests(int maxRequestsInCntr){
		
		boolean ret = false;
		int currentCnt = getRequestsCount();
		if (currentCnt > maxRequestsInCntr)
		{
			requests.subList(maxRequestsInCntr, currentCnt).clear();
			ret = true;
		}
	
		LOGGER.debug("Requested prune of requests in container {}. Max Allowed {}. Current {}. Prune performed = {}.", 
				getId(),
				maxRequestsInCntr, 
				currentCnt,
				ret);
		
		return ret;
	}

	// rules
	public List<Rule> getRules() {
		return rules == null ? Collections.emptyList() : rules;
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

	/**
	 * Returns the best rule matching the provided request. If there is no such
	 * rule a default rule will be returned.
	 * 
	 * @param request
	 *            the request against which a rule is searched for.
	 * 
	 * @return the best rule matching the request
	 */
	@JsonIgnore
	public Rule getBestRule(Request request) {
		
		Objects.requireNonNull(request, "Request cannot be null!");

		if (request.getContainer() != null) {
			if (!getId().equals(request.getContainer().getId())) {
				throw new IllegalArgumentException("The provided request "
						+ request.getId()
						+ " is not for the current container " + getId()
						+ " It is for container "
						+ request.getContainer().getId());
			}
		}

		Rule retRule = getRules()
				.stream()
				.filter(rule -> (request.getMethod().equals(
						rule.getRequestMethod()) || "ALL".equals(rule
						.getRequestMethod()))).findFirst()
				.orElse(getDefaultRule());

		return retRule;
	}

	private Rule getDefaultRule() {
		Rule rule = new Rule();
		rule.setResponseCode(HttpStatus.OK.value());
		rule.setResponseBody("OK");
		return rule;
	}
}