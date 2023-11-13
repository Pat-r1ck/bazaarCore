package com.okit.resourceservice.mappers;

import com.okit.resourceservice.dto.UpdateUserProfileRequest;
import com.okit.resourceservice.models.UserProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class UserProfileMapper
{
    @Mapping(target = "dob",ignore = true)
    @Mapping(target = "icon", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateUserProfile(UpdateUserProfileRequest request, @MappingTarget UserProfile userProfile);
}
