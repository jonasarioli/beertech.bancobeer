package com.beertech.banco.infrastructure.rest.configuration.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.beertech.banco.infrastructure.repository.mysql.ContaRepository;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlConta;

@Service
public class AutenticacaoService implements UserDetailsService {

	@Autowired
	private ContaRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MySqlConta> usuario = repository.findByUsername(username);
		if(usuario.isPresent()) {
			return usuario.get();			
		}
			
		throw new UsernameNotFoundException("Dados invalidos");
		
	}
	
}
