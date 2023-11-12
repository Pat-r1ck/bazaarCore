package com.okit.profileservice.exceptions;

public class EmailNotFoundException extends RuntimeException
{
    public EmailNotFoundException(String message)
    {
        super(message);
    }
}
