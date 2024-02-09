package br.com.test.backend.service;

import br.com.test.backend.dto.TransferRequest;
import br.com.test.backend.entity.Transfer;
import br.com.test.backend.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    public Transfer agendarTransferencia(TransferRequest transferRequest) {
        BigDecimal taxa = calcularTaxa(transferRequest.getDataTransferencia(), transferRequest.getValorTransferencia());

        if (taxa.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("Não é possível realizar a transferência com base nas regras estabelecidas.");
        }

        // Criar entidade Transfer
        Transfer transfer = new Transfer();
        transfer.setContaOrigem(transferRequest.getContaOrigem());
        transfer.setContaDestino(transferRequest.getContaDestino());
        transfer.setValorTransferencia(transferRequest.getValorTransferencia());
        transfer.setTaxa(taxa);
        transfer.setDataTransferencia(transferRequest.getDataTransferencia());
        transfer.setDataAgendamento(LocalDate.now());

        // Salvar no banco de dados
        return transferRepository.save(transfer);
    }


    public List<Transfer> obterExtrato() {
        return transferRepository.findAll();
    }

    private BigDecimal calcularTaxa(LocalDate dataTransferencia, BigDecimal valorTransferencia) {
        long diasAteTransferencia = ChronoUnit.DAYS.between(LocalDate.now(), dataTransferencia);

        if (diasAteTransferencia >= 0 && diasAteTransferencia <= 10) {
            return BigDecimal.valueOf(3.00).add(valorTransferencia.multiply(BigDecimal.valueOf(0.025)));
        } else if (diasAteTransferencia >= 11 && diasAteTransferencia <= 20) {
            return BigDecimal.ZERO;
        } else if (diasAteTransferencia >= 21 && diasAteTransferencia <= 30) {
            return BigDecimal.ZERO;
        } else if (diasAteTransferencia >= 31 && diasAteTransferencia <= 40) {
            return BigDecimal.ZERO;
        } else if (diasAteTransferencia >= 41 && diasAteTransferencia <= 50) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.ZERO;
    }
}
