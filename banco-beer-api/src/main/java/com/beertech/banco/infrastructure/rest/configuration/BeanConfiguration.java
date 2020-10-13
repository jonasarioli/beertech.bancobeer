package com.beertech.banco.infrastructure.rest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beertech.banco.domain.service.BancoService;
import com.beertech.banco.domain.service.impl.BancoServiceImpl;
import com.beertech.banco.infrastructure.repository.mysql.repository.impl.MySqlContaRepositoryImpl;

@Configuration
public class BeanConfiguration {

	@Autowired
	private MySqlContaRepositoryImpl mySqlContaRepositoryImpl;
	
	@Bean
    public BancoService bancoService() {
        return new BancoServiceImpl(mySqlContaRepositoryImpl);
    }
}
