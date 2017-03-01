package eu.balev.pushme.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalErrorController {
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = ObjectNotFoundException.class)
	public String objectNotFoundHandler() {
		return "objectnotfound";
	}
}
