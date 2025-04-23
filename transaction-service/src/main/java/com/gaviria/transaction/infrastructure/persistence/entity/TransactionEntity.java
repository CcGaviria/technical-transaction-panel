package com.gaviria.transaction.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "El monto debe ser mayor que cero.")
    @Column(nullable = false)
    private int monto;

    @NotBlank(message = "El nombre del comercio no puede estar vacío.")
    @Column(nullable = false)
    private String comercio;

    @NotBlank(message = "El nombre del Tenpista no puede estar vacío.")
    @Column(nullable = false)
    private String usuario;

    @PastOrPresent(message = "La fecha no puede estar en el futuro.")
    @Column(nullable = false)
    private LocalDateTime fecha;
}
