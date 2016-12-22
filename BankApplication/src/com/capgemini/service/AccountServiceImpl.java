package com.capgemini.service;

import java.util.StringJoiner;

import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitailBalanceException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {

	AccountRepository accountRepository;
	Account account;
	public AccountServiceImpl(AccountRepository accountRepository){
		this.accountRepository = accountRepository;
		account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
	}
	
	@Override
	public Account createAccount(int accountNumber, int amount) throws InsufficientInitailBalanceException {
		if(amount < 500){
			throw new InsufficientInitailBalanceException();
		}
		if(accountRepository.save(account)){
			return account;
		}
		return null;
	}

	@Override
	public int showBalance(int accountNumber) throws InvalidAccountNumberException {
		Account account = accountRepository.searchAccount(accountNumber);
		if(null==account)
			throw new InvalidAccountNumberException();
			
		return account.getAmount();
	}

	@Override
	public int withdrawAmount(int accountNumber, int amount)
			throws InsufficientBalanceException, InvalidAccountNumberException {
		Account account = accountRepository.searchAccount(accountNumber);
		if(null==account)
			throw new InvalidAccountNumberException();
		if(amount > account.getAmount()){
			throw new InsufficientBalanceException();
		}
		return  account.getAmount()-amount;
	}

	@Override
	public int depositAmont() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public StringJoiner fundTransfer() {
		// TODO Auto-generated method stub
		return null;
	}

}
