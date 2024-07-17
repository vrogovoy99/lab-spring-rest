package com.cydeo.exception;

import com.cydeo.dto.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

        ExceptionWrapper exceptionWrapper = new ExceptionWrapper(HttpStatus.CONFLICT.value(), exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exceptionWrapper);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionWrapper> notFoundException(NotFoundException exception, HttpServletRequest request){

        ExceptionWrapper exceptionWrapper = new ExceptionWrapper(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exceptionWrapper);
    }

}
