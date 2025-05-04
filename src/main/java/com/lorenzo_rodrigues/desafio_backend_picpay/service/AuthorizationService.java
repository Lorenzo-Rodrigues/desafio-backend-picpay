package com.lorenzo_rodrigues.desafio_backend_picpay.service;

import com.lorenzo_rodrigues.desafio_backend_picpay.client.AuthorizationClient;
import com.lorenzo_rodrigues.desafio_backend_picpay.client.dto.AuthorizationResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean authorize(){
        AuthorizationResponse authorization= authorizationClient.isAuthorized();
        if (!authorization.data().authorization() || authorization.status().equals("fail")){
            throw new RuntimeException("not authorized");
        }
        return true;
    }
}
