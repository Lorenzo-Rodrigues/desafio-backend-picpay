package com.lorenzo_rodrigues.desafio_backend_picpay.controller;

import com.lorenzo_rodrigues.desafio_backend_picpay.controller.dto.TransferRequest;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Transfer;
import com.lorenzo_rodrigues.desafio_backend_picpay.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    @Operation(description = "Create transfer")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Returns transfer with updated wallets "),
                @ApiResponse(responseCode = "422", description = "Transfer is not valid or unauthorized")
    })
    public ResponseEntity<Transfer> createTransfer (@RequestBody TransferRequest request){

        var transfer = transferService.createTransfer(request);

        return ResponseEntity.ok(transfer);
    }
}
