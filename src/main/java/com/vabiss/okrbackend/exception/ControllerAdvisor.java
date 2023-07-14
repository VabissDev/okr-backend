package com.vabiss.okrbackend.exception;

import com.vabiss.okrbackend.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(CurrentStateResourceException.class)
    public ResponseEntity<ErrorResponseDto> exceptionHandler(CurrentStateResourceException ex) {
        return ResponseEntity.status(409).body(ErrorResponseDto.of(409, ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> exceptionHandler(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ErrorResponseDto.of(404, ex.getMessage()));
    }

    @ExceptionHandler(VerificationTokenExpiredException.class)
    public ResponseEntity<ErrorResponseDto> exceptionHandler(VerificationTokenExpiredException ex) {
        return ResponseEntity.status(410).body(ErrorResponseDto.of(410, ex.getMessage()));
    }

}
