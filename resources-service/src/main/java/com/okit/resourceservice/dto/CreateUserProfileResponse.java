package com.okit.resourceservice.dto;

import java.util.Map;

import com.okit.resourceservice.models.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CreateUserProfileResponse extends DomainResponse
{
    public CreateUserProfileResponse(String message, int code, Map<String,UserProfile> data)
    {
        super(message, code);
        this.data = data;
    }
    private Map<String, UserProfile> data;
}
