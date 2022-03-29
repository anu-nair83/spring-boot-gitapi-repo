package com.telstra.codechallenge.exception;

import org.springframework.core.Ordered;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        Object value = ex.getValue();
        String message = String.format("'%s' should be a valid '%s' and '%s' isn't",
                name, type, value);
        return buildResponseEntity(new CustomApiException(HttpStatus.BAD_REQUEST, message, ex));
     }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    protected ResponseEntity<Object> handleServletRequestParameterException(UnsatisfiedServletRequestParameterException ex) {
        String error;
        error = ex.getMessage();
        return buildResponseEntity(new CustomApiException(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(TimeoutException.class)
    protected ResponseEntity<Object> handleTimeoutException(TimeoutException ex) {
        String error;
        error = "The request has timed out!";
        return buildResponseEntity(new CustomApiException(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(SocketTimeoutException.class)
    protected ResponseEntity<Object> handleSocketTimeoutException(SocketTimeoutException ex) {
        String error;
        error = "The socket has timed out!";
        return buildResponseEntity(new CustomApiException(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        String error;
        ex.printStackTrace();
        error = ex.getMessage();
        return buildResponseEntity(new CustomApiException(HttpStatus.BAD_REQUEST, error, ex));
    }

   private ResponseEntity<Object> buildResponseEntity(CustomApiException error) {
       return new ResponseEntity<Object>(error, error.getStatus());
   }
}
