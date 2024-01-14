package com.example.controller;

import com.example.dto.TradeRequest;
import com.example.entity.Trade;
import com.example.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {


    private final TradeService tradeService;

    @PostMapping("/buy")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<Trade> buyStock(@RequestBody TradeRequest tradeRequest) {
        try {
            Trade trade = tradeService.executeBuy(
                    tradeRequest.getPortfolioId(),
                    tradeRequest.getStockSymbol(),
                    tradeRequest.getQuantity()
            );
            return new ResponseEntity<>(trade, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sell")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<Trade> sellStock(@RequestBody TradeRequest tradeRequest) {
        try {
            Trade trade = tradeService.executeSell(
                    tradeRequest.getPortfolioId(),
                    tradeRequest.getStockSymbol(),
                    tradeRequest.getQuantity()
            );
            return new ResponseEntity<>(trade, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

