package com.example.dto.response;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeResponse {

    private Long portfolioId;
    private String stockSymbol;
    private Integer quantity;
    private String TradeType;
}
