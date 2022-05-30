package com.vipdsilva.app.ws.exceptions;

import java.time.Instant;
import java.util.List;

public class ExceptionResponseMessage {
    
    private Instant time;
    private int status;
    private String error;
    private String exception;
    private List<String> messages;  

    public ExceptionResponseMessage(Instant now, int statusCode,String reasonPhrase,
     String exception, List<String> errorMessages) {
        this.time = now;
        this.status = statusCode;
        this.error = reasonPhrase;
        this.exception = exception;
        this.messages = errorMessages;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

}
