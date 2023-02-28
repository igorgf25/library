package com.igor.library.exception;

import com.igor.library.model.error.ApiErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( { EntityAlreadyExist.class } )
    public ResponseEntity<ApiErrorDTO> handleEntityAlreadyExist(EntityAlreadyExist ex, WebRequest request){

        String error = "Recurso já cadastrado";

        ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());

    }

    @ExceptionHandler( { EntityInvalid.class } )
    public ResponseEntity<ApiErrorDTO> handleEntityInvalid(EntityInvalid ex, WebRequest request){

        String error = "Recurso não valido";

        ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());

    }

}
