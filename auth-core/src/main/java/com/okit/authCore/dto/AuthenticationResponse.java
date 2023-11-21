package com.okit.authCore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse extends DomainResponse
{
    public AuthenticationResponse(String message, int code, String token)
    {
        super(message, code);
        this.token = token;
    }

    private String token;
}
