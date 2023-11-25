package com.okit.domain.responses;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class GenericResponse
{
    public GenericResponse(String message, int code)
    {
        this.response = new HashMap<>();

        response.put("message", message);
        response.put("code", code);
    }

    public void add(String key, Object value)
    {
        response.put(key, value);
    }

    private final HashMap<String,Object> response;
}
