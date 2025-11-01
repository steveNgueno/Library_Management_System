package com.example.LMS.exceptions;

import com.example.LMS.dtos.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException ex, HttpServletRequest request){

        log.error(ex.getMessage());

        ErrorDetails error = ErrorDetails.of(
              HttpStatus.NOT_FOUND.value(),
              "Not found",
              "Invalid parameter: "+ex.getMessage(),
              request.getRequestURI()
      );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
