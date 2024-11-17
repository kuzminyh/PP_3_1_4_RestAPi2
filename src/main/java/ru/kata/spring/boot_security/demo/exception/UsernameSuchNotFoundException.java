package ru.kata.spring.boot_security.demo.exception;

import lombok.Data;

@Data
public class UsernameSuchNotFoundException extends RuntimeException{
    public UsernameSuchNotFoundException(String message) {
        super(message);
    }
}
