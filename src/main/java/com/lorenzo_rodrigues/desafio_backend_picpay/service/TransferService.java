package com.lorenzo_rodrigues.desafio_backend_picpay.service;

import com.lorenzo_rodrigues.desafio_backend_picpay.controller.dto.TransferRequest;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Transfer;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Wallet;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.WalletType;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.TransferRepository;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class TransferService {
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;

    public TransferService(WalletRepository walletRepository, TransferRepository transferRepository, AuthorizationService authorizationService) {
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
        this.authorizationService = authorizationService;
    }


    public Transfer createTransfer (TransferRequest transferRequest){
        var sender = findWalletByIdOrThrowException(transferRequest.payer());
        var receiver = findWalletByIdOrThrowException(transferRequest.payee());

        validate(sender,receiver,transferRequest.money());
        authorizationService.authorize();

        sender.debit(transferRequest.money());
        receiver.credit(transferRequest.money());

        var transfer = new Transfer(sender,receiver,transferRequest.money());

        log.info("updating both wallets balance...");
        walletRepository.save(sender);
        walletRepository.save(receiver);

        log.info("saving transaction {} ...",transfer);
        var newTransfer = transferRepository.save(transfer);

        // enviar notificacao

        return null;
    }

    public void validate(Wallet sender, Wallet receiver, BigDecimal money){
        log.info("validating transaction...");
        if(sender.getId().equals(receiver.getId())){
            throw new RuntimeException("Cannot make transfer to yourself");
        }
        if (sender.getWalletType() == WalletType.MERCHANT){
            throw new RuntimeException("WalletType merchant not allowed to transfer");
        }
        if (sender.getBalance().compareTo(money) <0){
            throw new RuntimeException("Insufficient balance");
        }

    }

    public Wallet findWalletByIdOrThrowException(Long id){
        log.info("fetching wallet: {} ...", id);
        return walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("wallet not found with id: " + id));
    }
}
