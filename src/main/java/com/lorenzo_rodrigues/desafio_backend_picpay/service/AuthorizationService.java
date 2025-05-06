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

    public void authorize(){
        log.info("authorizing transaction...");
        AuthorizationResponse authorization= authorizationClient.isAuthorized();
        if (!authorization.data().authorization() || authorization.status().equals("fail")){
            throw new TransferNotAuthorizedException();
        }
    }
}
