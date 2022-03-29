package cn.abstractmgs.common.exception;

public class UploadFailureException extends RuntimeException{

    public UploadFailureException(){
        super();
    }

    public UploadFailureException(String message){
        super(message);
    }
}
