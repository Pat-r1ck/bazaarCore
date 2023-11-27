package com.okit.resourceservice.exceptions;

import com.okit.domain.responses.GenericResponse;
import com.okit.resourceservice.constants.S3Constants;
import com.okit.resourceservice.constants.UserProfileCoreConstants;
import com.okit.resourceservice.models.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler
{
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<HashMap<String, Object>> handleConstraintViolationException(
            ConstraintViolationException exception
    )
    {
        final List<String> violations = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return new ResponseEntity<>(
                new GenericResponse("Invalid update request", 69)
                        .add("details", violations)
                        .getResponse()
                , HttpStatus.BAD_REQUEST
        );
    }


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
