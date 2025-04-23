package com.gaviria.transaction.interfaces.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTransactionRequest {

    @NotNull(message = "El ID de la transacción es obligatorio.")
    private Long id;

    @Positive(message = "El monto debe ser mayor que cero.")
    private int monto;

    @NotBlank(message = "El nombre del comercio no puede estar vacío.")
    private String comercio;

    @NotBlank(message = "El nombre del Tenpista no puede estar vacío.")
    private String usuario;

    @NotNull(message = "La fecha es obligatoria.")
    private LocalDateTime fecha;
}
