package com.capgemini.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitailBalanceException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	Account account;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		accountService= new AccountServiceImpl(accountRepository);
		account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
	}
	

	/*
	 * create account
	 * 1. when the amount is less than 500 system should throw exception
	 * 2. when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exception.InsufficientInitailBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitailBalanceException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitailBalanceException
	{
		when(accountRepository.save(account)).thenReturn(true);
		
		assertEquals(account, accountService.createAccount(101, 5000));
		
	}
	

	/*
	 * show balance
	 * 1. when the account number is invalid then throw an invalid account number exception
	 * 2. when the valid info is passed return amount
	 */
	
	@Test(expected=com.capgemini.exception.InvalidAccountNumberException.class)
	public void whenTheAccountNumberIsInvalidSystemShouldThrowException() throws InvalidAccountNumberException
	{	
		when(accountRepository.searchAccount(account.getAccountNumber())).thenReturn(account);
		
		accountService.showBalance(101);	
	}
	
	
	@Test
	public void whenTheValidInfoIsPassedAmountShouldReturned() throws InvalidAccountNumberException
	{		
		when(accountRepository.searchAccount(account.getAccountNumber())).thenReturn(account);
				
		assertEquals(5000, accountService.showBalance(account.getAccountNumber()));
	}
	
	/*
	 * withdraw amount
	 * 1. when the account number is invalid then throw an invalid account number exception
	 * 2. when the amount is more than current amount then system should throw invalid balance exception
	 * 3. when the amount is correct than return the remaining amount
	 * 
	 */
	@Test(expected = com.capgemini.exception.InvalidAccountNumberException.class)
	public void whenTheAccountNumberIsInvalidForWithdrawSystemShouldThrowException()
			throws InvalidAccountNumberException, InsufficientBalanceException {
		when(accountRepository.searchAccount(account.getAccountNumber())).thenReturn(account);
		accountService.withdrawAmount(122, 5000);
		
	}
	@Test(expected = com.capgemini.exception.InsufficientBalanceException.class)
	public void whenTheAmountisMoreSystemShouldThrowException()
			throws InvalidAccountNumberException, InsufficientBalanceException {
		when(accountRepository.searchAccount(account.getAccountNumber())).thenReturn(account);
		accountService.withdrawAmount(account.getAccountNumber(), 6000);
		
	}
	
	@Test
	public void whenTheAmountisCorrect()
			throws InvalidAccountNumberException, InsufficientBalanceException {
		when(accountRepository.searchAccount(account.getAccountNumber())).thenReturn(account);
		assertEquals(2000, accountService.withdrawAmount(account.getAccountNumber(), 3000));

	}
	
}
