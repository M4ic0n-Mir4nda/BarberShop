package com.dev.project.barbershop.exceptions;

public class ErrorWhenSavingOrDeleteException extends RuntimeException{
    public ErrorWhenSavingOrDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
