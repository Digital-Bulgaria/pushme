package eu.balev.pushme.service.recaptcha;

/**
 * A service for validating reCAPTCHA-s.
 */
public interface RecaptchaService {
	
	/**
	 * Verifies if the captcha response was valid.
	 * 
	 * @param remoteIp the remore IP of the user (optional)
	 * @param response the reCAPTCHA response
	 * 
	 * @return true if the response is valid
	 */
	boolean isResponseValid(String remoteIp, String response);
	
	/**
	 * Returns true if the service is properly configured false if there is no configuration.
	 * 
	 * @return true if the service is properly configured false if there is no configuration.
	 */
	boolean isServiceConfigured();
}
