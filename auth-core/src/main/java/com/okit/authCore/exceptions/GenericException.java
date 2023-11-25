package com.okit.authCore.exceptions;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException
{
    public GenericException(String msg, int code)
    {
        super(msg);
        this.code = code;
    }

    private final int code;
}
