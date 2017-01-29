package eu.balev.pushme.service.recaptcha;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RecaptchaServiceImpl.class);

	/**
	 * Encapsulates the reCAPTCHA response.
	 */
	private static class RecaptchaResponse {
		@JsonProperty("success")
		private boolean success;
		@JsonProperty("error-codes")
		private Collection<String> errorCodes;
	}

	private final RestTemplate restTemplate;

	@Value("${pushme.recaptcha.sitekey:#{null}}")
	private String recaptchaSiteKey;

	@Value("${pushme.recaptcha.secretkey:#{null}}")
	private String recaptchaSecretKey;

	@Value("${pushme.recaptcha.url:#{null}}")
	private String recaptchaUrl;

	@Autowired
	public RecaptchaServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public boolean isResponseValid(String remoteIp, String response) {

		if (!isServiceConfigured()) {
			throw new IllegalStateException("Please check if the service is configured before calling this... "
					+ "Call #isServiceConfigured");
		}

		try {
			RecaptchaResponse recaptchaResponse = restTemplate.postForEntity(
					recaptchaUrl,
					createBody(recaptchaSecretKey, remoteIp, response),
					RecaptchaResponse.class).getBody();

			LOGGER.debug("A captcha was solved. Result is {}.",
					recaptchaResponse.success);
			return recaptchaResponse.success;

		} catch (RestClientException e) {
			LOGGER.error(
					"Currently the captcha service is not available due to the following error!",
					e);
			return false;
		}
	}

	private MultiValueMap<String, String> createBody(String secret,
			String remoteIp, String response) {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("secret", secret);
		form.add("remoteip", remoteIp);
		form.add("response", response);
		return form;
	}

	@Override
	public boolean isServiceConfigured() {
		return !isAnyNullOrEmptry(recaptchaSiteKey, recaptchaSecretKey,
				recaptchaUrl);
	}

	private boolean isAnyNullOrEmptry(String... args) {
		for (String arg : args) {
			if (arg == null || arg.isEmpty())
				return true;
		}
		return false;
	}
}
