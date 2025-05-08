package com.lorenzo_rodrigues.desafio_backend_picpay.controller;

import com.lorenzo_rodrigues.desafio_backend_picpay.controller.dto.TransferRequest;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Transfer;
import com.lorenzo_rodrigues.desafio_backend_picpay.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<Transfer> createTransfer (TransferRequest request){

        var transfer = transferService.createTransfer(request);

        return ResponseEntity.ok(transfer);
    }
}
