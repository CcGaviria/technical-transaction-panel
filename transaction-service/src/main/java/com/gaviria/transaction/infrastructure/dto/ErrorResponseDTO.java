package com.gaviria.transaction.infrastructure.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ErrorResponse", description = "Respuesta de error general para la API")
public class ErrorResponseDTO {

    @Schema(example = "2025-04-20T14:32:00")
    private LocalDateTime timestamp;

    @Schema(example = "429")
    private int status;

    @Schema(example = "Too Many Requests")
    private String error;

    @Schema(example = "Se ha excedido el límite de peticiones permitidas.")
    private String details;

}