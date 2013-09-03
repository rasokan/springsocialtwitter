package com.learn.spring.social.twitter.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.learn.spring.social.twitter.domain.Account;

@Repository
public class JdbcAccountRepository implements AccountRepository{
	
	private final JdbcTemplate jdbcTemplate;
	
	private final PasswordEncoder passwordEncoder;
	
	protected static Logger logger4J = Logger.getLogger("controller");

	@Inject
	public JdbcAccountRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
		this.jdbcTemplate = jdbcTemplate;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public void createAccount(Account user) throws UsernameAlreadyInUseException{
		logger4J.debug("Entering into createAccount Method");
		try{
			jdbcTemplate.update(
					"insert into Account (firstName, lastName, userName, password) values (?, ?, ?, ?)",
					user.getFirstName(), user.getLastName(), user.getUserName(),
					passwordEncoder.encode(user.getPassword()));
		}
		catch(DuplicateKeyException e){
			throw new UsernameAlreadyInUseException(user.getUserName());
		}
		
	}
	
	public Account findAccountByUsername(String username) {
	
		logger4J.debug("Entering into findAccountByUsername Method");
		
		return jdbcTemplate.queryForObject("select username, firstName, lastName from Account where username = ?",new RowMapper<Account>(){

			public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Account(rs.getString("username"), null, rs.getString("firstName"), rs
						.getString("lastName"));
			}
		},username);
	}
}
