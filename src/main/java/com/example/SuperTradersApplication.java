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
		// !!! Role tablomu dolduracagim
		if(userRoleService.getAllUserRole().isEmpty()){
			userRoleService.save(RoleType.ADMIN);
			userRoleService.save(RoleType.CUSTOMER);

		}
		//!!! add user with Admin Role
		if(userService.countUserWithRole(RoleType.ADMIN)==0){
			UserRequest adminRequest  = new UserRequest();
			adminRequest.setUsername("Admin");
			adminRequest.setPassword("12345678");
			authenticationService.saveUser(adminRequest,"Admin");

		}
		//!!! 3 adet stock nesnesi olusturuyorum
		if(stockService.countStock()==0) {
			StockRequest apple = new StockRequest();
			apple.setCurrentPrice(70.00);
			apple.setRate(70.00);
			apple.setSymbol("AAP");
			stockService.saveStock(apple);

			StockRequest aaa = new StockRequest();
			aaa.setCurrentPrice(80.00);
			aaa.setRate(80.00);
			aaa.setSymbol("AAA");
			stockService.saveStock(aaa);

			StockRequest bbb = new StockRequest();
			bbb.setCurrentPrice(90.00);
			bbb.setRate(90.00);
			bbb.setSymbol("BBB");
			stockService.saveStock(bbb);

		}
		//!!! add 5 User With Customer Role
		if(userService.countUserWithRole(RoleType.CUSTOMER)==0){
			UserRequest customer1  = new UserRequest();
			customer1.setUsername("Customer1");
			customer1.setPassword("12345678");
			authenticationService.saveUser(customer1,"Customer");
			tradeService.buyStock("Customer1","AAA", 10 );


			UserRequest customer2  = new UserRequest();
			customer2.setUsername("Customer2");
			customer2.setPassword("12345678");
			authenticationService.saveUser(customer2,"Customer");
			tradeService.buyStock("Customer2","AAA", 10 );

			UserRequest customer3  = new UserRequest();
			customer3.setUsername("Customer3");
			customer3.setPassword("12345678");
			authenticationService.saveUser(customer3,"Customer");
			tradeService.buyStock("Customer3","AAA", 10 );

			UserRequest customer4  = new UserRequest();
			customer4.setUsername("Customer4");
			customer4.setPassword("12345678");
			authenticationService.saveUser(customer4,"Customer");
			tradeService.buyStock("Customer4","AAA", 10 );

			UserRequest customer5  = new UserRequest();
			customer5.setUsername("Customer5");
			customer5.setPassword("12345678");
			authenticationService.saveUser(customer5,"Customer");
			tradeService.buyStock("Customer5","AAA", 10 );
		}
		if(stockService.countStock()==0) {
			StockRequest apple = new StockRequest();
			apple.setCurrentPrice(120.00);
			apple.setRate(120.00);
			apple.setSymbol("AAP");
			stockService.saveStock(apple);

			StockRequest aaa = new StockRequest();
			aaa.setCurrentPrice(100.00);
			aaa.setRate(100.00);
			aaa.setSymbol("AAA");
			stockService.saveStock(aaa);

			StockRequest bbb = new StockRequest();
			bbb.setCurrentPrice(90.00);
			bbb.setRate(90.00);
			bbb.setSymbol("BBB");
			stockService.saveStock(bbb);

		}
	}
}
