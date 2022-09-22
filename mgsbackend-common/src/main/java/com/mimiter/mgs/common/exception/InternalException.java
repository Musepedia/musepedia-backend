package com.mimiter.mgs.common.exception;

public class InternalException extends RuntimeException{

    public InternalException(){
        super();
    }

    public InternalException(String message){
        super(message);
    }
}
