package eu.balev.pushme.web;

import javax.validation.constraints.NotNull;

public class RuleForm {
	
	@NotNull
	private String requestMethod;
	
	@NotNull
	private Integer responseCode;
	
	private String responseBody;
	
	@NotNull
	private String containerId;
	
	public String getContainerId() {
		return containerId;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
}
