package com.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradeRequest {

    @NotNull(message = "Please select Portfolio")
    private Long portfolioId;
    @NotNull(message = "Please select Symbol")
    private String stockSymbol;
    @NotNull(message = "Please enter quantity")
    private Integer quantity;
}
