package br.com.test.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransferRequest {
    private String contaOrigem;
    private String contaDestino;
    private BigDecimal valorTransferencia;
    private LocalDate dataTransferencia;
}
