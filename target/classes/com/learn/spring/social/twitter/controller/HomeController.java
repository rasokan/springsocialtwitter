package com.learn.spring.social.twitter.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.log4j.Logger;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learn.spring.social.twitter.repository.AccountRepository;

@Controller
public class HomeController {

	private Provider<ConnectionRepository> connectionRepositoryProvider;
		
	private  AccountRepository accountRepository;
	
	protected static Logger logger4J = Logger.getLogger("controller");

	@Inject
	public HomeController(Provider<ConnectionRepository> connectionRepositoryProvider, AccountRepository accountRepository) {
		this.connectionRepositoryProvider = connectionRepositoryProvider;
		this.accountRepository = accountRepository;
	}
	
	@RequestMapping("/")
	public String home(Principal currentUser, Model model) {
		model.addAttribute("connectionsToProviders", getConnectionRepository().findAllConnections());
		model.addAttribute(accountRepository.findAccountByUsername(currentUser.getName()));
		logger4J.debug("Entering into home Model");
		return "home";
	}
	
	private ConnectionRepository getConnectionRepository() {
		logger4J.debug("Entering into Connection Repository");
		return connectionRepositoryProvider.get();
	}
}
