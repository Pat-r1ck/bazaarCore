package com.okit.authCore.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateEmailExceptionResponse extends DomainResponse
{
    public DuplicateEmailExceptionResponse(String message, int code)
    {
        super(message,code);
    }
}
