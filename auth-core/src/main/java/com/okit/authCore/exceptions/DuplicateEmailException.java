package com.okit.authCore.exceptions;

public class DuplicateEmailException extends RuntimeException
{
    public DuplicateEmailException(String msg)
    {
        super(msg);
    }
}
