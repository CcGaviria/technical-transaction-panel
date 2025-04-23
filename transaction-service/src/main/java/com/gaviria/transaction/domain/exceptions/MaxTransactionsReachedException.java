package com.gaviria.transaction.domain.exceptions;

public class MaxTransactionsReachedException extends RuntimeException {
    public MaxTransactionsReachedException(String message) {
        super(message);
    }
}
