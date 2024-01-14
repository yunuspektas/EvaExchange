package com.example.service;

import com.example.entity.Portfolio;
import com.example.entity.PortfolioStock;
import com.example.entity.Stock;
import com.example.entity.Trade;
import com.example.entity.enums.TradeType;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TradeService {

    private final UserService userService;
    private final StockService stockService;
    private final PortFolioService portFolioService;
    private final TradeRepository tradeRepository;

    public Trade executeBuy(Long portfolioId, String stockSymbol, Integer quantity) {

        Stock stock = stockService.checkStockWithSymbol(stockSymbol);
        Portfolio portfolio = portFolioService.checkPortfolioById(portfolioId);

        Trade trade = new Trade();
        trade.setPortfolio(portfolio);
        trade.setStock(stock);
        trade.setPrice(stock.getCurrentPrice());
        trade.setQuantity(quantity);
        trade.setTradeType(TradeType.BUY);

        return tradeRepository.save(trade);
    }

    public Trade executeSell(Long portfolioId, String stockSymbol, Integer quantity) {

        Stock stock = stockService.findBySymbol(stockSymbol);
        Portfolio portfolio = portFolioService.checkPortfolioById(portfolioId);

        // kullanicinin portfoyunde yeterli stock var mi kontrolu
        int availableQuantity = getAvailableQuantityInPortfolio(portfolio, stock, TradeType.SELL, quantity);

        if (availableQuantity < quantity) {
            throw new ResourceNotFoundException("Yetersiz hisse senedi miktarı.");
        }

        Trade trade = new Trade();
        trade.setPortfolio(portfolio);
        trade.setStock(stock);
        trade.setPrice(stock.getCurrentPrice());
        trade.setQuantity(quantity);
        trade.setTradeType(TradeType.SELL);
        return tradeRepository.save(trade);
    }

    private int getAvailableQuantityInPortfolio(Portfolio portfolio, Stock stock,
                                                TradeType tradeType, int requestedQuantity) {
        // Portföyde bulunan hisse senetleri
        List<PortfolioStock> portfolioStocks = portfolio.getPortfolioStocks();

        // Belirli bir hisse senedinden kaç adet bulunduğunu hesaplamak için dongu
        int totalQuantity = 0;
        for (PortfolioStock portfolioStock : portfolioStocks) {
            if (portfolioStock.getStock().equals(stock)) {
                // Portföyde bu hisse senedi bulunuyorsa, miktarı topluyorum
                totalQuantity += portfolioStock.getQuantity().intValue();
            }
        }

        // Alım işlemi için kontrol
        if (tradeType == TradeType.BUY) {
            // Kullanıcının almak istediği miktarı kontrol ediyorum
            if (totalQuantity >= requestedQuantity) {
                return requestedQuantity; // Kullanıcı talep ettiği kadar alabilir.
            } else {
                return 0; // Kullanıcı talep ettiği miktar kadar alamaz.
            }
        }

        // Satış işlemi için kontrol
        if (tradeType == TradeType.SELL) {
            // Kullanıcının satmak istediği miktarı kontrol ediyorum
            if (totalQuantity >= requestedQuantity) {
                return requestedQuantity; // Kullanıcı talep ettiği kadar satabilir.
            } else {
                return 0; // Kullanıcı talep ettiği miktar kadar satamaz.
            }
        }

        return 0;
    }

}
