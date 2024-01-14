package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class PortfolioStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Portföyde bulunan hisse senedi
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    // Portföyde bulunan hisse senedinin miktarı
    private BigDecimal quantity;

    // Portföy referansı
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

}