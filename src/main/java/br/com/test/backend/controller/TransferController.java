package br.com.test.backend.controller;

import br.com.test.backend.service.TransferService;
import br.com.test.backend.dto.TransferRequest;
import br.com.test.backend.entity.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/agendar")
    public ResponseEntity<Transfer> agendarTransferencia(@RequestBody TransferRequest transferRequest) {
        Transfer transfer = transferService.agendarTransferencia(transferRequest);
        return new ResponseEntity<>(transfer, HttpStatus.CREATED);
    }

    @GetMapping("/extrato")
    public ResponseEntity<List<Transfer>> obterExtrato() {
        List<Transfer> extrato = transferService.obterExtrato();
        return new ResponseEntity<>(extrato, HttpStatus.OK);
    }
}
