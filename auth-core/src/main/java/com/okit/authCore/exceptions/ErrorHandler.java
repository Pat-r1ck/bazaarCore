package com.okit.authCore.exceptions;

import com.okit.authCore.constants.JwtCoreConstants;
import com.okit.authCore.constants.UserCoreConstants;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler
{
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<String> handleUsernameNotFoundException(
            UsernameNotFoundException exception
    )
    {
        logger.error("username {} not found", exception.getMessage());
        return new ResponseEntity<>(
                String.format(UserCoreConstants.USER_NOT_FOUND_MSG, exception.getMessage()),
                HttpStatus.NOT_FOUND
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
