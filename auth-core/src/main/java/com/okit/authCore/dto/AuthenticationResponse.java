package com.okit.authCore.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthenticationResponse extends DomainResponse
{
    public AuthenticationResponse(String message, int code, Map<String,String> data )
    {
        super(message, code);
        this.data = data;
    }
    private Map<String, String> data;
}
