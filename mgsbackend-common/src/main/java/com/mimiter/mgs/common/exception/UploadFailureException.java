package com.mimiter.mgs.common.exception;

public class UploadFailureException extends RuntimeException{

    public UploadFailureException(){
        super();
    }

    public UploadFailureException(String message){
        super(message);
    }
}
