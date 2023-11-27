package com.okit.resourceservice.exceptions;

import com.okit.resourceservice.constants.S3Constants;
import com.okit.resourceservice.constants.UserProfileCoreConstants;
import com.okit.resourceservice.dto.GenericResponse;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler
{
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(
                new GenericResponse(errors.toString(), 400).getResponse(),
                HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler({ GenericException.class })
    public ResponseEntity<HashMap<String, Object>> handleGenericException(
        GenericException exception
    ){
        return new ResponseEntity<>(
            new GenericResponse(exception.getMessage(), exception.getCode()).getResponse(),
            exception.getStatus()
        );
    }

    // @ExceptionHandler({EmailNotFoundException.class})
    // public ResponseEntity<String> handleEmailNotFoundException(
    //         EmailNotFoundException exception
    // )
    // {
    //     return new ResponseEntity<>(
    //             String.format(UserProfileCoreConstants.PROFILE_NOT_FOUND_MSG, exception.getMessage()),
    //             HttpStatus.NOT_FOUND
    //     );
    // }

    // @ExceptionHandler({MissMatchFileException.class})
    // public ResponseEntity<String> handleMissMatchFileException(
    //         MissMatchFileException exception
    // )
    // {
    //     return new ResponseEntity<>(
    //             S3Constants.MISS_MATCH_FILE_MSG,
    //             HttpStatus.BAD_REQUEST
    //     );
    // }

    // @ExceptionHandler({ProductNotFoundException.class})
    // public ResponseEntity<String> handleProductNotFoundException(
    //         ProductNotFoundException exception
    // )
    // {
    //     return new ResponseEntity<>(
    //             String.format(S3Constants.PRODUCT_NOT_FOUND, exception.getMessage()),
    //             HttpStatus.BAD_REQUEST
    //     );
    // }
}
