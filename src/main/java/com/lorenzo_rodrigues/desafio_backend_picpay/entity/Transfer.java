package com.lorenzo_rodrigues.desafio_backend_picpay.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transfer")
@Getter
@Setter
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Wallet payer;

    @ManyToOne
    private Wallet payee;

    private BigDecimal moneyValue;

    private LocalDateTime timestamp;
}
