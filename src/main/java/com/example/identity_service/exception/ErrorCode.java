package com.example.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(203, "User already existed", HttpStatus.BAD_REQUEST),
    USER_NOTEXIST(206, "User not exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(207, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED_EXCEPTION(205, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(204, "Username must be at least {min} character", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(204, "Password must be at least {min} character", HttpStatus.BAD_REQUEST),
    KEY_INVALID(204, "Invalid key message", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(204, "You do not have permissionss", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "You age must be at least {min}", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
