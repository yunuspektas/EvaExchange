package com.example.payload.messages;

public class ErrorMessages {

    private ErrorMessages() {
	}

	public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found ";
	public static final String NOT_FOUND_PORTFOLIO_MESSAGE = "Error: The user does not have a portfolio.";

	public static final String INSUFFICIENT_STOCK_QUANTITY_MESSAGE = "Error : Insufficient stock quantity";

	public static final String ALREADY_REGISTER_STOCK_SYMBOL_MESSAGE = "Error: Stock with symbol %s is already registered";

	public static final String NOT_FOUND_STOCK_WITH_SYMBOL = "Error: Stock is not found with Symbol : %s";


	public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
	public static final String ROLE_ALREADY_EXIST = "Role already exist in DB";

	public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "Error: User with username %s is already registered";


}
