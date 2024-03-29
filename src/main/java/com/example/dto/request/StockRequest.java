package com.example.dto.request;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRequest {
    @Pattern(regexp = "^[A-Z]{3}$", message = "Sembol, tamamen büyük harflerden oluşan 3 karakter olmalıdır.")
    @NotNull(message = "Please enter Symbol of Stock")
    private String symbol;

    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Rate must be a number with exactly 2 decimal digits.")
    @NotNull(message = "Please enter the rate of Stock")
    private String rate;

    @NotNull(message = "Please enter current price of Stock")
    private Double currentPrice;
}
