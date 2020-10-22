package com.beertech.banco.infrastructure.rest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beertech.banco.domain.service.ContaService;
import com.beertech.banco.domain.service.OperacaoService;
import com.beertech.banco.domain.service.ProfileService;
import com.beertech.banco.domain.service.impl.ContaServiceImpl;
import com.beertech.banco.domain.service.impl.OperacaoServiceImpl;
import com.beertech.banco.domain.service.impl.ProfileServiceImpl;
import com.beertech.banco.infrastructure.amqp.sender.MessageSender;
import com.beertech.banco.infrastructure.amqp.service.RelayService;
import com.beertech.banco.infrastructure.amqp.service.impl.RelayServiceImpl;
import com.beertech.banco.infrastructure.repository.mysql.repository.impl.MySqlContaRepositoryImpl;
import com.beertech.banco.infrastructure.repository.mysql.repository.impl.MySqlOperacaoRepositoryImpl;
import com.beertech.banco.infrastructure.repository.mysql.repository.impl.MySqlProfileRepositoryImpl;

@Configuration
public class BeanConfiguration {

	@Autowired
	private MySqlContaRepositoryImpl mySqlContaRepositoryImpl;
	
	@Autowired
	private MySqlProfileRepositoryImpl mySqlProfileRepositoryImpl;
	
	@Autowired
	private MySqlOperacaoRepositoryImpl mySqlOperacaoRepositoryImpl;
	
	@Autowired
	MessageSender messageSender;
	
	@Bean
    public ContaService bancoService() {
        return new ContaServiceImpl(mySqlContaRepositoryImpl);
    }
	
	@Bean
    public ProfileService profileService() {
        return new ProfileServiceImpl(mySqlProfileRepositoryImpl);
    }
	
	@Bean
    public RelayService relayService() {
        return new RelayServiceImpl(messageSender, mySqlContaRepositoryImpl);
    }
	
	@Bean
	public OperacaoService operacaoService() {
		return new OperacaoServiceImpl(mySqlContaRepositoryImpl, mySqlOperacaoRepositoryImpl);
	}
}
