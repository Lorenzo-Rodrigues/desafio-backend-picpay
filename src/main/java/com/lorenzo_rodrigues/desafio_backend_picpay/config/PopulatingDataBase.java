package com.lorenzo_rodrigues.desafio_backend_picpay.config;

import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Wallet;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.WalletType;
import com.lorenzo_rodrigues.desafio_backend_picpay.repository.WalletRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class PopulatingDataBase {

    @Bean
    CommandLineRunner populateDB(WalletRepository walletRepository) {
        return args -> {
            if (walletRepository.count() == 0) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                Wallet wallet1 = new Wallet(null, "Fernando Neto", "12345678", "fernando@email.com",
                        encoder.encode("123"), new BigDecimal(1000), WalletType.COMMON);

                Wallet wallet2 = new Wallet(null, "Yan Oliveira", "12345670", "yan@email.com",
                        encoder.encode("123"), new BigDecimal(1000), WalletType.MERCHANT);

                walletRepository.saveAll(List.of(wallet1, wallet2));
            }
        };
    }
}
