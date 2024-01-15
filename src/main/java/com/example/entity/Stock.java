package com.example.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Sembol, tamamen büyük harflerden oluşan 3 karakter olmalıdır.")
    @Column(unique = true)
    private String symbol;

    private Double rate;

    private Double currentPrice;
}
