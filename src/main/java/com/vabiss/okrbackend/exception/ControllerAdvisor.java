package com.vabiss.okrbackend.exception;

import com.vabiss.okrbackend.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(CurrentStateResourceException.class)
    public ResponseEntity<ResponseDto> exceptionHandler(CurrentStateResourceException ex) {
        return ResponseEntity.status(409).body(ResponseDto.of(409, ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto> exceptionHandler(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ResponseDto.of(404, ex.getMessage()));
    }

    @ExceptionHandler(VerificationTokenExpiredException.class)
    public ResponseEntity<ResponseDto> exceptionHandler(VerificationTokenExpiredException ex) {
        return ResponseEntity.status(410).body(ResponseDto.of(410, ex.getMessage()));
    }

}
