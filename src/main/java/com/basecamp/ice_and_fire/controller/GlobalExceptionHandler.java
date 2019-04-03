package com.basecamp.ice_and_fire.controller;

import com.basecamp.ice_and_fire.exception.NotFoundCharacterException;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    protected ResponseEntity<JSONObject> constraintViolationExceptionHandler(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        StringBuilder builder = new StringBuilder();

        for (ConstraintViolation<?> violation : violations) {
            builder.append(violation.getMessage());
        }

        JSONObject response = new JSONObject();
        response.put("message", builder.toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundCharacterException.class)
    @ResponseBody
    public ResponseEntity<JSONObject> notFoundCharacterExceptionHandler(NotFoundCharacterException ex) {

        JSONObject response = new JSONObject();
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
