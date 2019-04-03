package com.basecamp.ice_and_fire.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundCharacterException extends RuntimeException {

    public NotFoundCharacterException(String message) {
        super(message);
    }
}
