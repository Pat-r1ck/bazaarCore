package com.okit.profileservice.controllers;

import com.okit.profileservice.dto.UpdateUserProfileIconRequest;
import com.okit.profileservice.dto.UpdateUserProfileRequest;
import com.okit.profileservice.dto.UpdateUserProfileResponse;
import com.okit.profileservice.models.UserProfile;
import com.okit.profileservice.services.UserProfileService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class UserProfileController
{
    @GetMapping()
    public ResponseEntity<UserProfile> getProfile(
            HttpServletRequest request
    )
    {
        String email = extractEmail(request);

        return new ResponseEntity<>(userProfileService.getUserProfile(email), HttpStatus.OK);
    }

    @PostMapping(value = "/user",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(
            @ModelAttribute UpdateUserProfileRequest profileRequest,
            HttpServletRequest request
    ) throws ParseException, IOException
    {
        final String email = extractEmail(request);

        return new ResponseEntity<>(userProfileService.updateUserProfile(profileRequest,email), HttpStatus.CREATED);
    }

    @PostMapping(value = "/icon",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfileIcon(
            @ModelAttribute UpdateUserProfileIconRequest profileRequest,
            HttpServletRequest request
    ) throws IOException
    {
        final String email = extractEmail(request);

        return new ResponseEntity<>(userProfileService.updateUserIcon(profileRequest.getFile(), email),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/icon")
    public ResponseEntity<UpdateUserProfileResponse> deleteUserProfileIcon(
            HttpServletRequest request
    )
    {
        final String email = extractEmail(request);

        return new ResponseEntity<>(userProfileService.deleteUserIcon(email), HttpStatus.OK);
    }

    private String extractEmail(HttpServletRequest request)
    {
        return request.getHeader("email");
    }

    @Autowired
    private final UserProfileService userProfileService;
}
