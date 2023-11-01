package com.okit.profileservice.mappers;

import com.okit.profileservice.dto.UpdateUserProfileRequest;
import com.okit.profileservice.models.UserProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class UserProfileMapper
{
    @Mapping(target = "dob",ignore = true)
    @Mapping(target = "icon", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateUserProfile(UpdateUserProfileRequest request, @MappingTarget UserProfile userProfile);
}
