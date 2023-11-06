package com.okit.profileservice.services.implementations;

import com.okit.profileservice.constants.UserProfileCoreConstants;
import com.okit.profileservice.dto.UpdateUserProfileRequest;
import com.okit.profileservice.dto.UpdateUserProfileResponse;
import com.okit.profileservice.exceptions.EmailNotFoundException;
import com.okit.profileservice.mappers.UserProfileMapper;
import com.okit.profileservice.models.UserProfile;
import com.okit.profileservice.repositories.UserProfileRepository;
import com.okit.profileservice.services.S3Service;
import com.okit.profileservice.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService
{
    @Override
    public UserProfile getUserProfile(String email)
    {
        return userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

    @Override
    public UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest request, String email)
            throws ParseException, IOException
    {
        final String DATE_FORMAT = "dd/MM/yyyy";
        final String fileName = "icon_" + email;

        final Optional<UserProfile> profileOptional = userProfileRepository.findByEmail(email);

        final MultipartFile icon = request.getIcon();

        if(icon != null)
        {
            s3.uploadFile(fileName, icon);
        }

        UserProfile userProfile = profileOptional.isEmpty() ?
            UserProfile.builder()
                    .email(email)
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .academicYear(request.getAcademicYear())
                    .dob(new SimpleDateFormat(DATE_FORMAT).parse(request.getDob()))
                    .icon(icon == null ? null : fileName)
                    .rating(0)
                    .build() :
                profileOptional.get();

        profileMapper.updateUserProfile(request, userProfile);

        userProfileRepository.save(userProfile);

        return UpdateUserProfileResponse.builder()
                .msg(String.format(UserProfileCoreConstants.SUCCESSFULLY_UPDATE_USER_PROFILE_MSG, email))
                .build();
    }

    @Override
    public UpdateUserProfileResponse deleteUserIcon(String email)
    {
        UserProfile userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        userProfile.setIcon(null);

        final String fileName = "icon_" + email;

        s3.deleteFile(fileName);

        userProfileRepository.save(userProfile);

        return UpdateUserProfileResponse.builder()
                .msg(String.format(UserProfileCoreConstants.SUCCESSFULLY_DELETE_USER_ICON_MSG, fileName))
                .build();
    }

    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    private final UserProfileMapper profileMapper;

    @Autowired
    private final S3Service s3;
}
