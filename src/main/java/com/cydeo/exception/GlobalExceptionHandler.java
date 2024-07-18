package com.cydeo.exception;

import com.cydeo.dto.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // using response wrapper
//    @ExceptionHandler(AlreadyExistsException.class)
//    public ResponseEntity<ResponseWrapper> alreadyExistsException(AlreadyExistsException exception){
//
//        ResponseWrapper responseWrapper = ResponseWrapper.builder().success(false)
//                .code(409).message(exception.getMessage())
//                .build();
//        return ResponseEntity.status(HttpStatus.CONFLICT)
//                .body(responseWrapper);
//
//    }

    // using custom error wrapper
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionWrapper> alreadyExistException(AlreadyExistsException exception, HttpServletRequest request){

        exception.printStackTrace();

        ExceptionWrapper exceptionWrapper = new ExceptionWrapper(HttpStatus.CONFLICT.value(), exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exceptionWrapper);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionWrapper> notFoundException(NotFoundException exception, HttpServletRequest request){

        exception.printStackTrace();

        ExceptionWrapper exceptionWrapper = new ExceptionWrapper(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exceptionWrapper);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionWrapper> invalidObjectException(MethodArgumentNotValidException exception, HttpServletRequest request){
        exception.printStackTrace();

        ExceptionWrapper exceptionWrapper = new ExceptionWrapper(HttpStatus.BAD_REQUEST.value(), "invalid input(s)", request.getRequestURI());
        //create an empty list to store validation errors
        List<ValidationException> validationExceptions = new ArrayList<>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {

            String errorField = ((FieldError) error).getField();
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            String reason = error.getDefaultMessage();

            ValidationException validationException = new ValidationException(errorField, rejectedValue, reason);

            validationExceptions.add(validationException);
        }

        //set the list for exceptionWrapper field
        exceptionWrapper.setValidationExceptionList(validationExceptions);
        exceptionWrapper.setErrorCount(validationExceptions.size());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionWrapper);
    }


}
