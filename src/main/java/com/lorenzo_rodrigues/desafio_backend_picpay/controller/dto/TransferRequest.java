package com.lorenzo_rodrigues.desafio_backend_picpay.controller.dto;

import java.math.BigDecimal;

public record TransferRequest(Long payer, Long payee, BigDecimal money) {
}
