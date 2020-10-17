package com.beertech.banco.infrastructure.rest.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

	private String username;
	private String password;
	
	public void setUsername(String email) {
		this.username = email;
	}
	public void setPassword(String senha) {
		this.password = senha;
	}
	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(username, password);
	}
}
