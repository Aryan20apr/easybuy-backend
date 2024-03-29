package com.example.easypay.utils.exceptionUtil;

public class DuplicateVoteException extends RuntimeException {

    public DuplicateVoteException (String message) {
        super(message);
    }
}
