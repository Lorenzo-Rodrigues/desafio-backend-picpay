package com.lorenzo_rodrigues.desafio_backend_picpay.client;

import com.lorenzo_rodrigues.desafio_backend_picpay.client.dto.AuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "AuthorizationExternalAPI",
            url = "https://util.devi.tools/api/v2/authorize")
public interface AuthorizationClient {

    @GetMapping
    AuthorizationResponse isAuthorized();

    }

