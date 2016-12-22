package com.capgemini.service;

import java.util.StringJoiner;

import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitailBalanceException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.model.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitailBalanceException;
	int showBalance(int accountNumber) throws InvalidAccountNumberException;
	int depositAmont();
	StringJoiner fundTransfer();
	int withdrawAmount(int accountNumber, int amount) throws InsufficientBalanceException, InvalidAccountNumberException;
}
