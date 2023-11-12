package com.okit.profileservice.exceptions;

public class ProductNotFoundException extends RuntimeException
{
    public ProductNotFoundException(String msg)
    {
        super(msg);
    }
}
