package com.lorenzo_rodrigues.desafio_backend_picpay.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transfer")
@Getter
@Setter
@NoArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_sender_id")
    private Wallet payer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_receiver_id")
    private Wallet payee;

    private BigDecimal moneyValue;

    @CreationTimestamp
    private LocalDateTime timestamp;

    public Transfer(Wallet payer, Wallet payee, BigDecimal moneyValue) {
        this.payer= payer;
        this.payee= payee;
        this.moneyValue = moneyValue;
    }
}
