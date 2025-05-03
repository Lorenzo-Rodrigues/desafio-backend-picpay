package com.lorenzo_rodrigues.desafio_backend_picpay.repository;

import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
}
