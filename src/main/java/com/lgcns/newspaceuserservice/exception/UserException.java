package com.lgcns.newspaceuserservice.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private UserResponseStatus status;

    public UserException(UserResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
