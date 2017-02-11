package eu.balev.pushme.service.request;

import javax.servlet.http.HttpServletRequest;

import eu.balev.pushme.domain.Request;

public interface RequestService {
	Request mapRequest(HttpServletRequest request);
}
