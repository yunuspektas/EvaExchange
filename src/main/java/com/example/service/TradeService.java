package com.example.service;

import com.example.dto.response.TradeResponse;
import com.example.entity.*;
import com.example.entity.enums.TradeType;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.mappers.TradeMapper;
import com.example.payload.messages.ErrorMessages;
import com.example.repository.PortoFolioStockRepository;
import com.example.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TradeService {

    private final UserService userService;
    private final StockService stockService;
    private final PortFolioService portFolioService;
    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;
    private final PortoFolioStockRepository portoFolioStockRepository;

    public Trade executeBuy(HttpServletRequest request, String stockSymbol, Integer quantity) {

        Stock stock = stockService.checkStockWithSymbol(stockSymbol);
        // requesti gonderen kullaniciya ulasiyorum
        String username = (String) request.getAttribute("username");
        User customer = userService.findByUsername(username);
        // kullaniciya ait bir portfolio var mi kontrolu
        Portfolio portfolio = customer.getPortfolio();
        if(portfolio == null){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_PORTFOLIO_MESSAGE);
        }
        List<PortfolioStock> portfolioStocks = portfolio.getPortfolioStocks();
       for(PortfolioStock ps : portfolioStocks){
           if(ps.getStock().equals(stock)){
               ps.setQuantity(ps.getQuantity()+ quantity);
               portoFolioStockRepository.save(ps);
           } else {
               PortfolioStock ps1 = new PortfolioStock();
               ps1.setQuantity(quantity);
               ps1.setPortfolio(portfolio);
               ps1.setStock(stock);
               portoFolioStockRepository.save(ps1);
           }
       }

        //portfolio.getPortfolioStocks().add(portfolioStock);

        Trade trade = new Trade();
        trade.setPortfolio(portfolio);
        trade.setStock(stock);
        trade.setPrice(stock.getCurrentPrice());
        trade.setQuantity(quantity);
        trade.setUser(customer);
        trade.setTradeType(TradeType.BUY);

        return tradeRepository.save(trade);
    }

    public Trade executeSell(HttpServletRequest request, String stockSymbol, Integer quantity) {

        Stock stock = stockService.findBySymbol(stockSymbol);
        // requesti gonderen kullaniciya ulasiyorum
        String username = (String) request.getAttribute("username");
        User customer = userService.findByUsername(username);
        // kullaniciya ait bir portfolio var mi kontrolu
        Portfolio portfolio = customer.getPortfolio();
        if(portfolio == null){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_PORTFOLIO_MESSAGE);
        }
        int availableQuantity;
        // kullanicinin portfoyunde yeterli stock var mi kontrolu :
        if(customer.getPortfolio().getPortfolioStocks().stream().anyMatch(ps->ps.getStock().equals(stock))){
            availableQuantity = getAvailableQuantityInPortfolio(portfolio, stock, TradeType.SELL, quantity);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_STOCK_WITH_SYMBOL, stockSymbol));
        }

        if (availableQuantity < quantity) {
            throw new ResourceNotFoundException(ErrorMessages.INSUFFICIENT_STOCK_QUANTITY_MESSAGE);
        }
        List<PortfolioStock> portfolioStocks = portfolio.getPortfolioStocks();
        for(PortfolioStock ps : portfolioStocks){
            if(ps.getStock().equals(stock)){
                portoFolioStockRepository.save(ps);
            }
        }

        Trade trade = new Trade();
        trade.setPortfolio(portfolio);
        trade.setStock(stock);
        trade.setPrice(stock.getCurrentPrice());
        trade.setQuantity(quantity);
        trade.setUser(customer);
        trade.setTradeType(TradeType.SELL);

        // Kullanıcının portföyündeki satılan hisse senedi miktarını güncelle
        updatePortfolioStock(portfolio, stock, quantity);

        return tradeRepository.save(trade);
    }

    public void buyStock(String username, String stockSymbol, Integer quantity ){
        Stock stock = stockService.findBySymbol(stockSymbol);
        User customer = userService.findByUsername(username);
        // kullaniciya ait bir portfolio var mi kontrolu
        Portfolio portfolio = customer.getPortfolio();
        if(portfolio == null){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_PORTFOLIO_MESSAGE);
        }
        PortfolioStock portfolioStock = new PortfolioStock();
        portfolioStock.setStock(stock);
        portfolioStock.setPortfolio(portfolio);
        portfolioStock.setQuantity(quantity);
        portoFolioStockRepository.save(portfolioStock);
        portfolio.getPortfolioStocks().add(portfolioStock);

        Trade trade = new Trade();
        trade.setPortfolio(portfolio);
        trade.setStock(stock);
        trade.setPrice(stock.getCurrentPrice());
        trade.setQuantity(quantity);
        trade.setUser(customer);
        trade.setTradeType(TradeType.BUY);

        tradeRepository.save(trade);
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
    private void updatePortfolioStock(Portfolio portfolio, Stock stock, int soldQuantity) {
        List<PortfolioStock> portfolioStocks = portfolio.getPortfolioStocks();
        for (PortfolioStock portfolioStock : portfolioStocks) {
            if (portfolioStock.getStock().equals(stock)) {
                // Portföyde bu hisse senedi bulunuyorsa, satılan miktarı düşürün
                Integer currentQuantity = portfolioStock.getQuantity();
                Integer newQuantity = currentQuantity-soldQuantity;
                portfolioStock.setQuantity(newQuantity);
            }
        }
    }

    public List<TradeResponse> getAllTrade(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        User customer = userService.findByUsername(username);
        return tradeRepository.findByUser(customer)
                .stream()
                .map(tradeMapper::mapTradeToTradeResponse)
                .collect(Collectors.toList());
    }
}
