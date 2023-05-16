package org.gfg.expenseTracker.exceptionHandling;

public class NoResourceFoundException extends RuntimeException{
    public NoResourceFoundException(String message) {
        super(message);
    }
}
