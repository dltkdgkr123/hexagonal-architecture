package com.sh.hexagonal.application.service;

public class AccountIdEmptyException extends RuntimeException{

    AccountIdEmptyException() {
        super("expected account ID not to be empty");
    }
}
