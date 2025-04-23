package com.gaviria.transaction.interfaces.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaviria.transaction.application.usecases.CreateTransactionUseCase;
import com.gaviria.transaction.application.usecases.DeleteTransactionUseCase;
import com.gaviria.transaction.application.usecases.GetTransactionsByUserUseCase;
import com.gaviria.transaction.application.usecases.GetTransactionsUseCase;
import com.gaviria.transaction.application.usecases.UpdateTransactionUseCase;
import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.infrastructure.dto.ErrorResponseDTO;
import com.gaviria.transaction.interfaces.dto.DeleteTransactionResponse;
import com.gaviria.transaction.interfaces.dto.TransactionRequest;
import com.gaviria.transaction.interfaces.dto.TransactionResponse;
import com.gaviria.transaction.interfaces.dto.UpdateTransactionRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Transacciones", description = "Operaciones para administrar transacciones de Tenpistas")
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    private final UpdateTransactionUseCase updateTransactionUseCase;

    private final DeleteTransactionUseCase deleteTransactionUseCase;

    private final GetTransactionsByUserUseCase getTransactionsByUserUseCase;
    
    private final GetTransactionsUseCase getTransactionsUseCase;

    @Operation(summary = "Crear una transacción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transacción creada exitosamente"),
            @ApiResponse(responseCode = "409", description = "Límite de transacciones alcanzado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class), examples = @ExampleObject(name = "MaxTransactionsReached", summary = "Límite alcanzado", value = """
                    {
                      "timestamp": "2025-04-20T14:30:00",
                      "status": 409,
                      "error": "Conflict",
                      "details": "El usuario ya tiene 100 transacciones."
                    }
                    """))),
            @ApiResponse(responseCode = "429", description = "Límite de peticiones excedido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class), examples = @ExampleObject(name = "RateLimitExceeded", summary = "Demasiadas peticiones", value = """
                    {
                      "timestamp": "2025-04-20T14:30:00",
                      "status": 429,
                      "error": "Too Many Requests",
                      "details": "Se ha excedido el límite de peticiones permitidas."
                    }
                    """)))
    })
    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionRequest request) {
        Transaction tx = createTransactionUseCase.execute(request.getUsuario(), request.getMonto(),
                request.getComercio(), request.getFecha());
        return ResponseEntity.status(HttpStatus.CREATED).body(tx);
    }

    @Operation(summary = "Actualizar una transacción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Reglas de negocio violadas", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = {
                    @ExampleObject(name = "Validación fallida", value = """
                            {
                              "timestamp": "2025-04-20T14:30:00",
                              "status": 400,
                              "error": "Validación fallida",
                              "details": {
                                "usuario": "El nombre del Tenpista no puede estar vacío."
                              }
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = {
                    @ExampleObject(name = "Transacción no encontrada", value = """
                            {
                              "timestamp": "2025-04-20T14:30:00",
                              "status": 404,
                              "error": "Not Found",
                              "details": "No se encontró la transacción con ID 999."
                            }
                            """)
            })),
            @ApiResponse(responseCode = "429", description = "Límite de peticiones excedido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class), examples = @ExampleObject(name = "RateLimitExceeded", summary = "Demasiadas peticiones", value = """
                    {
                      "timestamp": "2025-04-20T14:30:00",
                      "status": 429,
                      "error": "Too Many Requests",
                      "details": "Se ha excedido el límite de peticiones permitidas."
                    }
                    """)))
    })
    @PutMapping
    public ResponseEntity<Transaction> update(@Validated @RequestBody UpdateTransactionRequest request) {
        Transaction updated = updateTransactionUseCase.execute(
                request.getId(),
                request.getUsuario(),
                request.getMonto(),
                request.getComercio(),
                request.getFecha());
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar una transacción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<DeleteTransactionResponse> deleteTransaction(@PathVariable Long transactionId) {
        deleteTransactionUseCase.execute(transactionId);
        DeleteTransactionResponse response = new DeleteTransactionResponse("Transacción eliminada con éxito",
                transactionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtener transacciónes por usuario")
    @GetMapping("/{usuario}")
    public List<TransactionResponse> getTransactionsByUsuario(@PathVariable String usuario) {
        List<Transaction> transacciones = getTransactionsByUserUseCase.execute(usuario);
        return transacciones.stream()
                .map(tx -> TransactionResponse.builder()
                        .id(tx.getId())
                        .monto(tx.getMonto())
                        .comercio(tx.getComercio())
                        .usuario(tx.getUsuario())
                        .fecha(tx.getFecha())
                        .build())
                .collect(Collectors.toList());
    }


    @Operation(summary = "Obtener transacciónes")
    @GetMapping()
    public List<TransactionResponse> getTransactions() {
        List<Transaction> transacciones = getTransactionsUseCase.execute();
        return transacciones.stream()
                .map(tx -> TransactionResponse.builder()
                        .id(tx.getId())
                        .monto(tx.getMonto())
                        .comercio(tx.getComercio())
                        .usuario(tx.getUsuario())
                        .fecha(tx.getFecha())
                        .build())
                .collect(Collectors.toList());
    }

}