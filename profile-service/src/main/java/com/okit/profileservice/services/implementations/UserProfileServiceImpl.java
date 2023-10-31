package com.okit.profileservice.services.implementations;

import com.okit.profileservice.constants.UserProfileCoreConstants;
import com.okit.profileservice.dto.UpdateUserProfileRequest;
import com.okit.profileservice.dto.UpdateUserProfileResponse;
import com.okit.profileservice.exceptions.EmailNotFoundException;
import com.okit.profileservice.mappers.UserProfileMapper;
import com.okit.profileservice.models.UserProfile;
import com.okit.profileservice.repositories.UserProfileRepository;
import com.okit.profileservice.services.StorageService;
import com.okit.profileservice.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
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
            throws ParseException, IOException {
        final String DATE_FORMAT = "dd/MM/yyyy";
        final String fileName = "icon_" + email;

        Optional<UserProfile> profileOptional = userProfileRepository.findByEmail(email);
        logger.info("Found user {}", email);

        if(profileOptional.isEmpty())
        {
            File image = convertImageToFile(request.getIcon());

            storageService.uploadFile(fileName, image);
            logger.info("Uploaded image");

            image.delete();
        }

        UserProfile userProfile = profileOptional.isEmpty() ?
            UserProfile.builder()
                    .email(email)
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .academicYear(request.getAcademicYear())
                    .dob(new SimpleDateFormat(DATE_FORMAT).parse(request.getDob()))
                    .icon(fileName)
                    .rating(0)
                    .build() :
                profileOptional.get();

        profileMapper.updateUserProfile(request, userProfile);
        userProfileRepository.save(userProfile);
        logger.info("user {} saved", email);

        return UpdateUserProfileResponse.builder()
                .msg(String.format(UserProfileCoreConstants.SUCCESSFULLY_UPDATE_USER_PROFILE_MSG, email))
                .build();
    }

    @Override
    public UpdateUserProfileResponse updateUserIcon(MultipartFile file, String email) throws IOException
    {
        UserProfile userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        File image = convertImageToFile(file);

        storageService.uploadFile(userProfile.getIcon(), image);
        logger.info("Updated image");

        image.delete();

        return UpdateUserProfileResponse.builder()
                .msg(String.format(UserProfileCoreConstants.SUCCESSFULLY_UPDATE_USER_PROFILE_MSG, email))
                .build();
    }

    @Override
    public UpdateUserProfileResponse deleteUserIcon(String email)
    {
        final String fileName = "icon_" + email;

        storageService.deleteFile(fileName);

        return UpdateUserProfileResponse.builder()
                .msg(String.format(UserProfileCoreConstants.SUCCESSFULLY_DELETE_USER_ICON_MSG, fileName))
                .build();
    }

    private File convertImageToFile(MultipartFile file) throws IOException
    {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.println(convFile);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    private final UserProfileMapper profileMapper;

    @Autowired
    private final StorageService storageService;

    private final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

}
