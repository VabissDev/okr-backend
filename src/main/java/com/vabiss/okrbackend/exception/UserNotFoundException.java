package com.vabiss.okrbackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {

    private String message;

    public UserNotFoundException() {

    }

    public UserNotFoundException(String message) {
        this.message = message;
    }

}
