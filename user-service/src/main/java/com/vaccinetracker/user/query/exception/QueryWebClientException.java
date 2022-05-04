package com.vaccinetracker.user.query.exception;

public class QueryWebClientException extends RuntimeException {

    public QueryWebClientException() {
        super();
    }

    public QueryWebClientException(String message) {
        super(message);
    }

    public QueryWebClientException(String message, Throwable t) {
        super(message, t);
    }
}
