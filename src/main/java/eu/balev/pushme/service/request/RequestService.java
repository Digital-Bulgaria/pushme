package eu.balev.pushme.service.request;

import javax.servlet.http.HttpServletRequest;

import eu.balev.pushme.domain.Request;

/**
 * Describes utility methods for request handling.
 */
public interface RequestService {
	/**
	 * Maps a servlet request to a PushMe request.
	 * 
	 * @param request the servlet request.
	 * 
	 * @return PushMe representation of the servlet request.
	 */
	Request mapRequest(HttpServletRequest request);
}
