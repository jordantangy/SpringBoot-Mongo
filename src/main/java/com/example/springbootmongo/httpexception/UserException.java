package com.example.springbootmongo.httpexception;


public class UserException extends RuntimeException{

    public UserException(String message) {
        super(message);
    }

}
