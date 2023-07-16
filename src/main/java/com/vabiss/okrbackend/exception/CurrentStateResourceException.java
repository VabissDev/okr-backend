package com.vabiss.okrbackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentStateResourceException extends RuntimeException {

    private String message;

    public CurrentStateResourceException() {

    }

    public CurrentStateResourceException(String message) {
        this.message = message;
    }

}
