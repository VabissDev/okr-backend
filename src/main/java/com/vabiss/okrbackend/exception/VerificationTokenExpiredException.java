package com.vabiss.okrbackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationTokenExpiredException extends RuntimeException {

    private String message;

    public VerificationTokenExpiredException() {

    }

    public VerificationTokenExpiredException(String message) {
        this.message = message;
    }

}
