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
    @Column(unique = true) // this field must be unique
    private String symbol;

    @DecimalMin(value = "0.00", message = "Hisse oranı en az 2 ondalık basamak içermelidir.")
    @DecimalMax(value = "99.99", message = "Hisse oranı en fazla 2 ondalık basamak içermelidir.")
    private Double rate;

    private Double currentPrice;
}
