package com.example.entity;

import com.example.entity.enums.TradeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Portfolio portfolio;

    @ManyToOne
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private Double price;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType; // Enum: BUY, SELL
}
