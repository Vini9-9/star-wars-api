package com.vipdsilva.app.ws.controller;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.vipdsilva.app.ws.exceptions.ExceptionResponseMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponseMessage handleInvalidParameterException(Exception ex) {

        return sendResponse(HttpStatus.BAD_REQUEST, ex);
    }

    private ExceptionResponseMessage sendResponse(HttpStatus status, Exception exception) {

        List<String> errorMessages = ((ConstraintViolationException) exception)
        .getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

        return new ExceptionResponseMessage(Instant.now(), status.value(),
         status.getReasonPhrase(), exception.getClass().toString(), errorMessages);
    }
}
