package com.gaviria.transaction.interfaces.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteTransactionResponse {
    private String message;
    private Long transactionId;

    public DeleteTransactionResponse(String message, Long transactionId) {
        this.message = message;
        this.transactionId = transactionId;
    }
}