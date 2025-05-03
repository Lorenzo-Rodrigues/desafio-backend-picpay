package com.lorenzo_rodrigues.desafio_backend_picpay.service;

import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Transfer;
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


    public Transfer transfer (Transfer transferDto){
        var sender = walletRepository.findById(transferDto.getPayer().getId());
        var receiver = walletRepository.findById(transferDto.getPayer().getId());

        // validação

        // atualização carteira

        // salvar transação

        // enviar notificacao

        return null;
    }
}
