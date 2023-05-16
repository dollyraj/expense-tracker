package org.gfg.expenseTracker.exceptionHandling;

public class NotFoundApiResponse {
    private String message;
    private boolean success;

    public NotFoundApiResponse(){

    }

    public NotFoundApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
