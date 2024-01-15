package com.example;

import com.example.dto.request.StockRequest;
import com.example.dto.request.UserRequest;
import com.example.entity.enums.RoleType;
import com.example.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuperTradersApplication implements CommandLineRunner {
	private final UserRoleService userRoleService;
	private final UserService userService;
	private final AuthenticationService authenticationService;
	private final StockService stockService;
	private final TradeService tradeService;

	public SuperTradersApplication(UserRoleService userRoleService,
                                   UserService userService,
                                   AuthenticationService authenticationService,
                                   StockService stockService, TradeService tradeService) {
		this.userRoleService = userRoleService;
		this.userService = userService;
        this.authenticationService = authenticationService;
        this.stockService = stockService;
        this.tradeService = tradeService;
    }

	public static void main(String[] args) {
		SpringApplication.run(SuperTradersApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createRoles();
		createAdmin();
		createStocks();
		createCustomers();
	}

	// Rollerin olusturulmasi: Eğer rol tablosu boşsa, Admin ve Customer rollerini ekler.
	private void createRoles() {
		if (userRoleService.getAllUserRole().isEmpty()) {
			userRoleService.save(RoleType.ADMIN);
			userRoleService.save(RoleType.CUSTOMER);
		}
	}

	// Admin  olusturulmasi: Eğer Admin rolüne sahip kullanıcı yoksa, bir tane ekler.
	private void createAdmin() {
		if (userService.countUserWithRole(RoleType.ADMIN) == 0) {
			createUserWithRole("Admin", "12345678", RoleType.ADMIN);
		}
	}

	// Stokların olusturulmasi: Eğer stok tablosu boşsa, örnek stoklar ekler.
	private void createStocks() {
		if (stockService.countStock() == 0) {
			createStock("AAP", 70.00);
			createStock("AAA", 80.00);
			createStock("BBB", 90.00);
		}
	}

	// Müşterilerin olusturulmasi: Eğer Customer rolüne sahip kullanıcı yoksa, örnek kullanıcılar ekler ve stok alımları yapar.
	private void createCustomers() {
		if (userService.countUserWithRole(RoleType.CUSTOMER) == 0) {
			for (int i = 1; i <= 5; i++) {
				createUserWithRole("Customer" + i, "12345678", RoleType.CUSTOMER);
				tradeService.buyStock("Customer" + i, "AAA", 10);
			}
		}
	}

	// Verilen kullanıcı adı, şifre ve rol ile yeni bir kullanıcı oluşturur.
	private void createUserWithRole(String username, String password, RoleType role) {
		UserRequest userRequest = new UserRequest();
		userRequest.setUsername(username);
		userRequest.setPassword(password);
		authenticationService.saveUser(userRequest, role.name());
	}

	// Verilen sembol ve fiyat ile yeni bir stok olusturur
	private void createStock(String symbol, double price) {
		StockRequest stockRequest = new StockRequest();
		stockRequest.setCurrentPrice(price);
		stockRequest.setRate(String.valueOf(price)); // Fiyat ve oran aynı varsayılıyor, gerektiğinde değiştirilebilir.
		stockRequest.setSymbol(symbol);
		stockService.saveStock(stockRequest);
	}
}


