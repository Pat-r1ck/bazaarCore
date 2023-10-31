package com.okit.profileservice.services;

import com.okit.profileservice.dto.UpdateUserProfileRequest;
import com.okit.profileservice.dto.UpdateUserProfileResponse;
import com.okit.profileservice.models.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@Service
public interface UserProfileService
{
    UserProfile getUserProfile(String email);
    UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest request, String email) throws ParseException, IOException;
    UpdateUserProfileResponse updateUserIcon(MultipartFile file, String email) throws IOException;
    UpdateUserProfileResponse deleteUserIcon(String email);
}
