package com.gb.balyanova.spring2.exceptions;

public class UserIsAlreadyExistsException extends Throwable {
    public UserIsAlreadyExistsException(String msg) {
        super(msg);
    }
}
