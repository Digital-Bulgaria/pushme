package eu.balev.pushme.service.request;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.Request;
import eu.balev.pushme.web.RequestBuilder;

@Service
public class RequestServiceImpl implements RequestService {

	@Override
	public Request mapRequest(HttpServletRequest httpRequest) {
		
		RequestBuilder pushMeReqBuilder = new RequestBuilder(httpRequest);

		Request pushMeReq = pushMeReqBuilder.buildHeaders().buildParameters()
				.buildGeneralProps().buildRequestFrom().build();

		return pushMeReq;
	}
}
