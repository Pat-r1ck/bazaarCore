package com.okit.resourceservice.controllers;

import com.okit.resourceservice.dto.CreateUserProfileRequest;
import com.okit.resourceservice.dto.CreateUserProfileResponse;
import com.okit.resourceservice.dto.UpdateUserProfileRequest;
import com.okit.resourceservice.services.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/resources/profile")
public class UserProfileController
{
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateUserProfileResponse> createUserProfile(
        @ModelAttribute @Valid CreateUserProfileRequest profileRequest,
        HttpServletRequest request
    ) throws ParseException, IOException
    {
        return new ResponseEntity<>(
            userProfileService.createUserProfile(profileRequest, extractEmail(request)),
            HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Object> getProfile(
        HttpServletRequest request
    ) {
        return new ResponseEntity<Object>(
            userProfileService.getUserProfile(extractEmail(request)).getResponse(),
            HttpStatus.OK
        );
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateUserProfile(
            @ModelAttribute UpdateUserProfileRequest profileRequest,
            HttpServletRequest request
    ) throws ParseException, IOException
    {
        return new ResponseEntity<>(
            userProfileService.updateUserProfile(profileRequest,extractEmail(request)).getResponse(), 
            HttpStatus.OK
        );
    }


    private String extractEmail(HttpServletRequest request)
    {
        return request.getHeader("email");
    }

    @Autowired
    private final UserProfileService userProfileService;
}
