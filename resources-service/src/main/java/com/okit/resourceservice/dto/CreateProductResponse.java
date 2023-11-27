package com.okit.resourceservice.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CreateProductResponse extends DomainResponse
{
    public CreateProductResponse(String message, int code, Map<String,String> data)
    {
        super(message, code);
        this.data = data;
    }
    private Map<String, String> data;
}
