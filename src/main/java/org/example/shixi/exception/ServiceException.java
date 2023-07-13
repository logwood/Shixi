package org.example.shixi.exception;

import java.security.Provider;

public class ServiceException extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
}
