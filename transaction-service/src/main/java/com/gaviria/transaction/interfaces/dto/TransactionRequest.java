package com.gaviria.transaction.interfaces.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @Positive(message = "El monto debe ser mayor que cero.")
    private int monto;

    @NotBlank(message = "El nombre del comercio no puede estar vacío.")
    private String comercio;

    @NotBlank(message = "El nombre del Tenpista no puede estar vacío.")
    private String usuario;

    @PastOrPresent(message = "La fecha no puede estar en el futuro.")
    private LocalDateTime fecha;
}