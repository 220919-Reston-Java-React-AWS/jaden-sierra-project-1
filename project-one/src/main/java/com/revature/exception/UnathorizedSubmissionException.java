package com.revature.exception;

public class UnathorizedSubmissionException extends Exception{
    public UnathorizedSubmissionException(String message){
        super(message);
    }
}
