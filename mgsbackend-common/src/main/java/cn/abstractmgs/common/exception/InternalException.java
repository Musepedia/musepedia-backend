package cn.abstractmgs.common.exception;

public class InternalException extends RuntimeException{

    public InternalException(){
        super();
    }

    public InternalException(String message){
        super(message);
    }
}
