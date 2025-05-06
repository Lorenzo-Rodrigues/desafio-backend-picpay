package com.lorenzo_rodrigues.desafio_backend_picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TransferNotAuthorizedException extends PicPayException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pd.setTitle("Transfer not authorized");
        pd.setDetail("Authorization service did not authorize this transfer");

        return pd;
    }
}
