package com.yash.springdatajparestful.exception;

public class EmailAlreadyExistException extends RuntimeException{
    String message;

    public EmailAlreadyExistException(String message)
    {
        super(message);
    }
}
