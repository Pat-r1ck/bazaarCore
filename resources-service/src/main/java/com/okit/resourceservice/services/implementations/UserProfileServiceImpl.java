package com.okit.resourceservice.services.implementations;

import com.okit.resourceservice.constants.S3Constants;
import com.okit.resourceservice.constants.UserProfileCoreConstants;
import com.okit.resourceservice.dto.CreateUserProfileRequest;
import com.okit.resourceservice.dto.CreateUserProfileResponse;
import com.okit.resourceservice.dto.GenericResponse;
import com.okit.resourceservice.dto.UpdateUserProfileRequest;
import com.okit.resourceservice.exceptions.GenericException;
import com.okit.resourceservice.mappers.UserProfileMapper;
import com.okit.resourceservice.models.UserProfile;
import com.okit.resourceservice.repositories.UserProfileRepository;
import com.okit.resourceservice.services.S3Service;
import com.okit.resourceservice.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService
{
    @Override
    public CreateUserProfileResponse 
        createUserProfile(CreateUserProfileRequest request, String email)
        throws ParseException, IOException, GenericException
    {
        Date dob = null;
        try {
            dob = new SimpleDateFormat(DATE_FORMAT).parse(request.getDob());
        }
        catch (ParseException e) {
            throw new GenericException("invalid date format, please use dd/mm/yyyy", 3);
        }
        
        Optional<UserProfile> existingUserProfile = userProfileRepository.findByEmail(email);
        if (existingUserProfile.isPresent()) {
            throw new GenericException("Email " + email + " already registered", 4);
        }

        final String fileName = "icon_" + email;
        final MultipartFile icon = request.getIcon();
        if(!icon.isEmpty())
        {
            s3.uploadFile(fileName, icon);
        }

        UserProfile userProfile = UserProfile.builder()
                .email(email)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .academicYear(request.getAcademicYear())
                .dob(dob)
                .icon(icon.isEmpty() ? null : S3Constants.S3_PREFIX + fileName)
                .rating(0)
                .build();

        userProfileRepository.save(userProfile);

        return new CreateUserProfileResponse(
                String.format(UserProfileCoreConstants.SUCCESSFULLY_CREATE_USER_PROFILE_MSG,email),
                100,
                Map.of("data", userProfile)
        );
    }

    @Override
    public GenericResponse 
        getUserProfile(String email)
        throws GenericException
    {
        UserProfile userProfile = userProfileRepository.findByEmail(email)
            .orElseThrow(() ->new GenericException("Email has not been register", 4, HttpStatus.NOT_FOUND));
        return new GenericResponse(
            "User found",
            100
        ) {
            {
                add("data", userProfile);
            }
        };
    }

    @Override
    public GenericResponse 
        updateUserProfile(UpdateUserProfileRequest request, String email)
        throws ParseException, IOException, GenericException
    {
        Date dob = null;
        if (request.getDob() != null) {
            try {
                dob = new SimpleDateFormat(DATE_FORMAT).parse(request.getDob());
            }
            catch (ParseException e) {
                throw new GenericException("invalid date format, please use dd/mm/yyyy", 3);
            }
        }

        UserProfile userProfile= userProfileRepository.findByEmail(email).orElseThrow(
            () -> new GenericException("Email has not been register", 4, HttpStatus.NOT_FOUND)       
        );
        
        if (dob != null) {
            userProfile.setDob(dob);
        }
        
        final String fileName = "icon_" + email;
        if (request.getIcon() != null && !request.getIcon().isEmpty()) {
            s3.uploadFile(fileName, request.getIcon());
        }        
        userProfile.setIcon(request.getIcon().isEmpty() ? null : S3Constants.S3_PREFIX + fileName);

        profileMapper.updateUserProfile(request, userProfile);
        userProfileRepository.save(userProfile);

        return new GenericResponse(
            String.format(UserProfileCoreConstants.SUCCESSFULLY_UPDATE_USER_PROFILE_MSG, email),
            200) {
                {
                    add("data", userProfile);
                }
        };
    }

    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    private final UserProfileMapper profileMapper;

    @Autowired
    private final S3Service s3;

    final String DATE_FORMAT = "dd/MM/yyyy";
}
