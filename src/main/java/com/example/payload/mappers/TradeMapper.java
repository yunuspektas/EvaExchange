package com.example.payload.mappers;

import com.example.dto.response.TradeResponse;
import com.example.entity.Trade;
import org.springframework.stereotype.Component;

@Component
public class TradeMapper {

    public TradeResponse mapTradeToTradeResponse(Trade trade){
        return TradeResponse.builder()
                .portfolioId(trade.getPortfolio().getId())
                .quantity(trade.getQuantity())
                .stockSymbol(trade.getStock().getSymbol())
                .TradeType(trade.getTradeType().name())
                .build();
    }
}
