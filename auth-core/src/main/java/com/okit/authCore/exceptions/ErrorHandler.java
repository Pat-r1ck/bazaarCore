package com.okit.authCore.exceptions;

import com.okit.authCore.constants.JwtCoreConstants;
import com.okit.authCore.constants.UserCoreConstants;
import com.okit.authCore.dto.DuplicateEmailExceptionResponse;
import com.okit.authCore.dto.ErrorResponse;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler
{
    @ExceptionHandler({EmailNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(
            EmailNotFoundException exception
    )
    {
        System.out.println("I AM HEREEEEEEEEEEEEEEEEEEEEEEe");
        logger.error("username {} not found", exception.getMessage());
        return new ResponseEntity<>(
                new ErrorResponse(
                        String.format(UserCoreConstants.USER_NOT_FOUND_MSG, exception.getMessage())
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({DuplicateEmailException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(
            DuplicateEmailException exception
    )
    {
        return new ResponseEntity<>(
                new ErrorResponse(
                        String.format(UserCoreConstants.DUPLICATE_EMAIL_MSG, exception.getMessage())
                ) ,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ SignatureException.class })
    public ResponseEntity<String> handleSignatureException(
            SignatureException exception
    )
    {
        logger.error("Jwt invalid signature");
        return new ResponseEntity<>(
                JwtCoreConstants.INVALID_SIGNATURE,
                HttpStatus.BAD_REQUEST
        );
    }

    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
}
