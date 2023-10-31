package com.okit.profileservice.exceptions;

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
}
