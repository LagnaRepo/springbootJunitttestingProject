package com.yash.springdatajparestful.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this class is used to handle all the specific exceptions at a single place globally.
// have extended the ResponseEntityExceptionHandler class to handle the java bean validation errors.
@ControllerAdvice
public class GlobalExceptionhandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> getCustomErrorForNotFound(ResourceNotFound e, WebRequest webRequest)
    {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                e.getMessage(),webRequest.getDescription(false),
                "USER_NOT_FOUND");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> getCustomErrorForEmailAlreadyExists(EmailAlreadyExistException e, WebRequest webRequest)
    {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                e.getMessage(),webRequest.getDescription(false),
                "USER_WITH_EMAIL_ALREADY_EXISTS");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> getCustomErrorForInternalServerException(Exception e, WebRequest webRequest)
    {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                e.getMessage(),webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
       Map<String,String> errors = new HashMap<>();
        List<ObjectError> Listerrors =ex.getAllErrors();
        Listerrors.forEach(e->{
                String field = ((FieldError)e).getField();
                String message = e.getDefaultMessage();
                errors.put(field,message);
        });
        return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

    }

}
