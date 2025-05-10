package com.lorenzo_rodrigues.desafio_backend_picpay.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
        @Schema(description = "default COMMON wallet with id 1",example = "1")
        @NotNull
        Long payer,
        @Schema(description = "default MERCHANT wallet with id 2 ",example = "2")
        @NotNull
        Long payee,
        @Schema(description = "default wallets starts with 1000 balance", example = "50")
        @NotNull
        BigDecimal money) {
}
