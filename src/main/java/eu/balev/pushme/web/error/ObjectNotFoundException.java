package eu.balev.pushme.web.error;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3078052008193088228L;

}
