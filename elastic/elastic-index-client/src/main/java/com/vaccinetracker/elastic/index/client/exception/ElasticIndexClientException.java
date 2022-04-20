package com.vaccinetracker.elastic.index.client.exception;

public class ElasticIndexClientException extends RuntimeException {

    public ElasticIndexClientException() {}

    public ElasticIndexClientException(String message) {
        super(message);
    }

    public ElasticIndexClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
