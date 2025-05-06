package com.lorenzo_rodrigues.desafio_backend_picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PicPayException extends RuntimeException{
    public ProblemDetail toProblemDetail(){
        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("PicPay internal server error");

        return pd;
    }
}
