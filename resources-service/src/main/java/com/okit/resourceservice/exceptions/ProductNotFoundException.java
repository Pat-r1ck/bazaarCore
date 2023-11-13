package com.okit.resourceservice.exceptions;

public class ProductNotFoundException extends RuntimeException
{
    public ProductNotFoundException(String msg)
    {
        super(msg);
    }
}
