package com.okit.authCore.dto;

public enum StatusCode
{
    REGISTER(0),
    AUTHENTICATE(1),
    ERROR(4);

    StatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public final int statusCode;
}
