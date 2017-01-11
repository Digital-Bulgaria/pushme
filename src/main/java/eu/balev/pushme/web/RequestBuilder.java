package eu.balev.pushme.web;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;


import eu.balev.pushme.domain.Header;
import eu.balev.pushme.domain.Parameter;
import eu.balev.pushme.domain.Request;

public class RequestBuilder {

	private final HttpServletRequest httpRequest;
	private Request pushMeRequest;
	
	public RequestBuilder(HttpServletRequest httpRequest)
	{
		this.httpRequest = httpRequest;
		
		pushMeRequest = new Request();
	}
	
	public RequestBuilder buildHeaders()
	{
		List<Header> pushMeHeaders = new LinkedList<>();
		
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		
		while(headerNames.hasMoreElements())
		{
			String headerName = headerNames.nextElement();
			Enumeration<String> headerValues = httpRequest.getHeaders(headerName);
			
			while (headerValues.hasMoreElements())
			{
				String headerValue = headerValues.nextElement();
				
				Header pushMeHeader = new Header();
				pushMeHeader.setName(headerName);
				pushMeHeader.setValue(headerValue);
				
				pushMeHeaders.add(pushMeHeader);
			}
		}
		
		pushMeRequest.setHeaders(pushMeHeaders);
		
		return this;
	}
	
	public RequestBuilder buildParameters()
	{
		List<Parameter> pushMeParameters = new LinkedList<>();
		
		Map<String, String[]> parameters = httpRequest.getParameterMap();
		
		parameters.
			entrySet().
			stream().
			forEach(entry -> 
				{
					String paramName = entry.getKey();
					Stream.of(entry.getValue()).forEach(paramValue ->
					{
						Parameter pushMeParameter = new Parameter();
						pushMeParameter.setName(paramName);
						pushMeParameter.setValue(paramValue);
						
						pushMeParameters.add(pushMeParameter);
					}
				);
				});
		
		pushMeRequest.setParameters(pushMeParameters);
		
		return this;
	}
	
	public RequestBuilder buildRequestFrom() 
	{
		String requestFrom = null;

		String xff = httpRequest.getHeader("X-FORWARDED-FOR");
		if (xff != null && !xff.isEmpty()) 
		{
			requestFrom = xff;
		} 
		else 
		{
			String remoteIP = httpRequest.getRemoteAddr();
			if (remoteIP != null && !remoteIP.isEmpty()) 
			{
				requestFrom = remoteIP;
			}
		}

		pushMeRequest.setRequestFrom(requestFrom);

		return this;
	}
	
	public RequestBuilder buildGeneralProps()
	{
		pushMeRequest.setMethod(httpRequest.getMethod());
		pushMeRequest.setRequestURI(httpRequest.getRequestURI());
		
		return this;
	}
	
	public Request build()
	{
		return pushMeRequest;
	}
}
