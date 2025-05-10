package com.lorenzo_rodrigues.desafio_backend_picpay.service;

import com.lorenzo_rodrigues.desafio_backend_picpay.controller.dto.TransferRequest;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Transfer;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Wallet;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.WalletType;
import com.lorenzo_rodrigues.desafio_backend_picpay.exception.InsufficientBalanceException;
import com.lorenzo_rodrigues.desafio_backend_picpay.exception.TransferNotAllowedForWalletType;
import com.lorenzo_rodrigues.desafio_backend_picpay.exception.TransferNotAllowedForYourself;
import com.lorenzo_rodrigues.desafio_backend_picpay.exception.WalletNotFoundException;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.TransferRepository;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TransferService {
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    public TransferService(WalletRepository walletRepository, TransferRepository transferRepository, AuthorizationService authorizationService, NotificationService notificationService) {
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transfer createTransfer (TransferRequest transferRequest){

        var sender = walletRepository.findById(transferRequest.payer())
                .orElseThrow(() -> new WalletNotFoundException(transferRequest.payer()));
        var receiver = walletRepository.findById(transferRequest.payee())
                .orElseThrow(() -> new WalletNotFoundException(transferRequest.payee()));

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

        CompletableFuture.runAsync(()-> notificationService.sendNotification(newTransfer));

        return newTransfer;
    }

    public void validate(Wallet sender, Wallet receiver, BigDecimal money){
        log.info("validating transaction...");
        if(sender.getId().equals(receiver.getId())){
            log.info("validation failed, payer id {} and payee id {} are the same",
                    sender,receiver);
            throw new TransferNotAllowedForYourself();
        }
        if (sender.getWalletType() == WalletType.MERCHANT){
            log.info("validation failed, this wallet: {} is merchant, therefore can not transfer. ",
                    sender);
            throw new TransferNotAllowedForWalletType();
        }
        if (sender.getBalance().compareTo(money) <0){
            log.info("validation failed, transfer value {} is greater than user {} balance",
                    money, sender);
            throw new InsufficientBalanceException();
        }
        log.info("completed validation, everything ok");
    }

    public Wallet findWalletByIdOrThrowException(Long id){
        log.info("fetching wallet: {} ...", id);
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
    }
}
