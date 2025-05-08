package com.lorenzo_rodrigues.desafio_backend_picpay.service;

import com.lorenzo_rodrigues.desafio_backend_picpay.client.AuthorizationClient;
import com.lorenzo_rodrigues.desafio_backend_picpay.client.dto.AuthorizationResponse;
import com.lorenzo_rodrigues.desafio_backend_picpay.exception.TransferNotAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorizationService {
    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public void authorize() {
        log.info("authorizing transaction...");
        try {
            authorizationClient.isAuthorized();

        } catch(Exception e){
            log.info("authorization failed");
            throw new TransferNotAuthorizedException();
        }
        log.info("authorization successful");
    }
}

