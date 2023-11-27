package com.okit.authCore.exceptions;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException
{
    public GenericException(String message, int code)
    {
        super(message);
        this.code = code;
    }
    private final int code;
}
