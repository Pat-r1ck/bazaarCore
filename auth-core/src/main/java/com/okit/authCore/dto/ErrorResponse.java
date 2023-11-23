package com.okit.authCore.dto;

import static com.okit.authCore.dto.StatusCode.ERROR;

public class ErrorResponse extends DomainResponse
{
    public ErrorResponse(String message)
    {
        super(message, ERROR.statusCode);
    }
}
