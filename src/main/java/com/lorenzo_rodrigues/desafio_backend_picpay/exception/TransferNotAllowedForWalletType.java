package com.lorenzo_rodrigues.desafio_backend_picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TransferNotAllowedForWalletType extends PicPayException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pd.setTitle("This wallet is not allowed to transfer");
        pd.setDetail("Wallet type merchant can not transfer money, only receive");

        return pd;
    }
}
