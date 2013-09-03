package com.learn.spring.social.twitter.repository;

import com.learn.spring.social.twitter.domain.Account;

public interface AccountRepository {
	
	void createAccount(Account account) throws UsernameAlreadyInUseException;

	Account findAccountByUsername(String username);

}
