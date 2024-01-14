package com.example.entity;


import lombok.*;

import javax.persistence.*;
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
    @Column(unique = true) // this field must be unique
    private String symbol;

    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Oran, tam olarak 2 ondalık basamak olmalıdır.")
    private Double rate;

    private Double currentPrice;
}
