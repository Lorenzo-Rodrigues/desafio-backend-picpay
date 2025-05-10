package com.lorenzo_rodrigues.desafio_backend_picpay.service;

import com.lorenzo_rodrigues.desafio_backend_picpay.controller.dto.TransferRequest;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Transfer;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Wallet;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.WalletType;
import com.lorenzo_rodrigues.desafio_backend_picpay.exception.InsufficientBalanceException;
import com.lorenzo_rodrigues.desafio_backend_picpay.exception.TransferNotAllowedForWalletType;
import com.lorenzo_rodrigues.desafio_backend_picpay.exception.TransferNotAllowedForYourself;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.TransferRepository;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransferServiceTest {
    @InjectMocks
    TransferService transferService;

    @Mock
    WalletRepository walletRepositoryMock;

    @Mock
    TransferRepository transferRepositoryMock;

    @Mock
    AuthorizationService authorizationServiceMock;

    @Test
    @DisplayName("Should create a transaction and update wallets when successful")
    void createTransfer_WhenSuccessful(){
        Wallet wallet1 = new Wallet(1L, "Gabriel Ferreira", "12345678", "gabriel@gmail.com",
                null, new BigDecimal(100), WalletType.COMMON);
        Wallet wallet2 = new Wallet(2L, "Fabio Augusto", "12345670", "fabio@gmail.com",
                null, new BigDecimal(100), WalletType.MERCHANT);

        var transferRequest = new TransferRequest(1L,2L,new BigDecimal(50));

        when(walletRepositoryMock.findById(1L)).thenReturn(Optional.of(wallet1));
        when(walletRepositoryMock.findById(2L)).thenReturn(Optional.of(wallet2));

        doNothing().when(authorizationServiceMock).authorize();

        var newTransfer = new Transfer(wallet1,wallet2,transferRequest.money());
        transferRepositoryMock.save(newTransfer);

        transferService.createTransfer(transferRequest);

        Assertions.assertEquals(newTransfer.getMoneyValue(),transferRequest.money());
        Assertions.assertEquals(new BigDecimal(50), wallet1.getBalance());
        Assertions.assertEquals(new BigDecimal(150), wallet2.getBalance());
    }
    @Test
    @DisplayName("Should throws InsufficientBalanceException when transaction value is greater than payer balance")
    void validate_ThrowsInsufficientBalanceException(){
        Wallet wallet1 = new Wallet(1L, "Ademar Neto", "12345678", "gabriel@gmail.com",
                null, new BigDecimal(100), WalletType.COMMON);
        Wallet wallet2 = new Wallet(2L, "Fellipe Ribeiro", "12345670", "fabio@gmail.com",
                null, new BigDecimal(100), WalletType.MERCHANT);

       assertThatExceptionOfType(InsufficientBalanceException.class)
               .isThrownBy(()-> transferService.validate(wallet1,wallet2,new BigDecimal(150)));
    }
    @Test
    @DisplayName("Should throws TransferNotAllowedForYourself when transfer payer and payee are the same")
    void validate_ThrowsTransferNotAllowedForYourselfException(){
        Wallet wallet1 = new Wallet(1L, "Victor Justino", "12345678", "gabriel@gmail.com",
                null, new BigDecimal(100), WalletType.COMMON);

        assertThatExceptionOfType(TransferNotAllowedForYourself.class)
                .isThrownBy(()-> transferService.validate(wallet1,wallet1,new BigDecimal(50)));
    }
    @Test
    @DisplayName("Should throws TransferNotAllowedForWalletType when payer's wallet type is merchant")
    void validate_ThrowsNotAllowedForWalletTypeException(){
        Wallet wallet1 = new Wallet(1L, "Ademar Neto", "12345678", "gabriel@gmail.com",
                null, new BigDecimal(100), WalletType.COMMON);
        Wallet wallet2 = new Wallet(2L, "Fellipe Ribeiro", "12345670", "fabio@gmail.com",
                null, new BigDecimal(100), WalletType.MERCHANT);

        assertThatExceptionOfType(TransferNotAllowedForWalletType.class)
                .isThrownBy(()-> transferService.validate(wallet2,wallet1,new BigDecimal(50)));
    }
}
