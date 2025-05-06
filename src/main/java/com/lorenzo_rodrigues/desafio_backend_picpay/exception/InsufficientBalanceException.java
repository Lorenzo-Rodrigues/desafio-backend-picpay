package com.lorenzo_rodrigues.desafio_backend_picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InsufficientBalanceException extends PicPayException{
    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pd.setTitle("Insufficient balance");
        pd.setDetail("You cannot transfer a greater value than your current balance.");

        return pd;
    }
}
