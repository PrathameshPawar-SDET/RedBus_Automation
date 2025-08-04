package com.redbus.exceptions;

public class InvalidBrowserException extends FrameworkException{

    public InvalidBrowserException(String message){
        super(message);
    }

    public  InvalidBrowserException(String message, Throwable cause){
        super(message,cause);
    }

}
