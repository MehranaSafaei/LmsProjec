package org.example.lms.exception;

public class MobileAlreadyTakenException extends Exception{

    public MobileAlreadyTakenException() {
        super();
    }

    public MobileAlreadyTakenException(String message) {
        super(message);
    }

    public MobileAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }


}
