package eu.balev.pushme.web;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import eu.balev.pushme.domain.Header;
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
	
	public Request build()
	{
		return pushMeRequest;
	}
}
