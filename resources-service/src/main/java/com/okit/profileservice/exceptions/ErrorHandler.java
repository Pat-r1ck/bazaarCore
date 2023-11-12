package com.okit.profileservice.exceptions;

import com.okit.profileservice.constants.S3Constants;
import com.okit.profileservice.constants.UserProfileCoreConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler
{
    @ExceptionHandler({EmailNotFoundException.class})
    public ResponseEntity<String> handleEmailNotFoundException(
            EmailNotFoundException exception
    )
    {
        return new ResponseEntity<>(
                String.format(UserProfileCoreConstants.PROFILE_NOT_FOUND_MSG, exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({MissMatchFileException.class})
    public ResponseEntity<String> handleMissMatchFileException(
            MissMatchFileException exception
    )
    {
        return new ResponseEntity<>(
                S3Constants.MISS_MATCH_FILE_MSG,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<String> handleProductNotFoundException(
            ProductNotFoundException exception
    )
    {
        return new ResponseEntity<>(
                String.format(S3Constants.PRODUCT_NOT_FOUND, exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
