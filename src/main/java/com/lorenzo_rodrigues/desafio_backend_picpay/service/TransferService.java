package com.lorenzo_rodrigues.desafio_backend_picpay.service;

import com.lorenzo_rodrigues.desafio_backend_picpay.controller.dto.TransferRequest;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Transfer;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Wallet;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.WalletType;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.TransferRepository;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;

    public TransferService(WalletRepository walletRepository, TransferRepository transferRepository) {
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
    }


    public Transfer transfer (TransferRequest transferRequest){
        var sender = walletRepository.findById(transferRequest.payer());
        var receiver = walletRepository.findById(transferRequest.payee());

        validate(transferRequest);

        // atualização carteira

        // salvar transação

        // enviar notificacao

        return null;
    }

    public void validate(TransferRequest transferRequest){
        var sender = findWalletByIdOrThrowException(transferRequest.payer());
        var receiver = findWalletByIdOrThrowException(transferRequest.payee());

        if(sender.getId().equals(receiver.getId())){
            throw new RuntimeException("Cannot make transfer to yourself");
        }
        if (sender.getWalletType() == WalletType.MERCHANT){
            throw new RuntimeException("WalletType merchant not allowed to transfer");
        }
        if (sender.getBalance().compareTo(transferRequest.money())<0){
            throw new RuntimeException("Insufficient balance");
        }

        // authorization service
    }

    public Wallet findWalletByIdOrThrowException(Long id){
        return walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("wallet not found with id: " + id));
    }
}
