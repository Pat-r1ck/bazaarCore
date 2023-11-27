package com.okit.resourceservice.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException
{
    public GenericException(String message, int code)
    {
        super(message);
        this.code = code;
    }

    public GenericException(String message, int code, HttpStatus status)
    {
        super(message);
        this.code = code;
        this.status = status;
    }

    private final int code;
    private HttpStatus status = HttpStatus.BAD_REQUEST;
}
