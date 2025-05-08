package com.lorenzo_rodrigues.desafio_backend_picpay.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_wallets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    public void debit(BigDecimal money) {
        this.balance = balance.subtract(money);
    }
    public void credit(BigDecimal money) {
        this.balance = balance.add(money);
    }

}
