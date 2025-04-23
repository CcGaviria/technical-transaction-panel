package com.gaviria.transaction.domain.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Transaction {
    private Long id;
    private int monto;
    private String comercio;
    private String usuario;
    private LocalDateTime fecha;

    public Transaction(Long id, int monto, String comercio, String usuario, LocalDateTime fecha) {
        this.id = id;
        this.monto = monto;
        this.comercio = comercio;
        this.usuario = usuario;
        this.fecha = fecha;
    }

}