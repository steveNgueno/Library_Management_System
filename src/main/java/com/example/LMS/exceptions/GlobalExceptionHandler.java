package com.example.LMS.exceptions;

import com.example.LMS.dtos.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

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
              ex.getMessage(),
              request.getRequestURI()
      );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BusinessLogicException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorDetails> handleBusinessLogicException(BusinessLogicException ex, HttpServletRequest request){

        log.error(ex.getMessage());

        ErrorDetails error = ErrorDetails.of(
                HttpStatus.NOT_ACCEPTABLE.value(),
                "Not acceptable",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){

        log.error(ex.getMessage());

        ErrorDetails error = ErrorDetails.of(
                HttpStatus.NOT_FOUND.value(),
                "Not found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){

        log.error(ex.getMessage());

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorDetails error = ErrorDetails.of(
                HttpStatus.NOT_FOUND.value(),
                "Validation failed",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDetails> handleException(Exception ex, HttpServletRequest request){

        log.error("Exception: {}", ex.getMessage());

        ErrorDetails error = ErrorDetails.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                String.valueOf(ex.getCause()),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


}
