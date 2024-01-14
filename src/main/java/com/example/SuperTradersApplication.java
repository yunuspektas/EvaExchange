package com.example;

import com.example.dto.UserRequest;
import com.example.entity.enums.RoleType;
import com.example.service.AuthenticationService;
import com.example.service.UserRoleService;
import com.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SuperTradersApplication implements CommandLineRunner {
	private final UserRoleService userRoleService;
	private final UserService userService;
	private final AuthenticationService authenticationService;

	public SuperTradersApplication(UserRoleService userRoleService,
                                   UserService userService, AuthenticationService authenticationService) {
		this.userRoleService = userRoleService;
		this.userService = userService;
        this.authenticationService = authenticationService;
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
		//!!! add 5 User With Customer Role
		//TODO : her müşterinin ticaret operasyonları için bir ALIŞ/SATIŞ işlem günlüğü olusturulacak
		if(userService.countUserWithRole(RoleType.CUSTOMER)==0){
			UserRequest customerRequest  = new UserRequest();
			customerRequest.setUsername("Customer1");
			customerRequest.setPassword("12345678");
			authenticationService.saveUser(customerRequest,"Customer");

			UserRequest customerRequest2  = new UserRequest();
			customerRequest2.setUsername("Customer2");
			customerRequest2.setPassword("12345678");
			authenticationService.saveUser(customerRequest2,"Customer");

			UserRequest customerRequest3  = new UserRequest();
			customerRequest3.setUsername("Customer3");
			customerRequest3.setPassword("12345678");
			authenticationService.saveUser(customerRequest3,"Customer");

			UserRequest customerRequest4  = new UserRequest();
			customerRequest4.setUsername("Customer4");
			customerRequest4.setPassword("12345678");
			authenticationService.saveUser(customerRequest4,"Customer");

			UserRequest customerRequest5  = new UserRequest();
			customerRequest5.setUsername("Customer5");
			customerRequest5.setPassword("12345678");
			authenticationService.saveUser(customerRequest5,"Customer");

		}
	}
}
