package com.beertech.banco.infrastructure.rest.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.domain.service.OperacaoService;
import com.beertech.banco.infrastructure.rest.controller.form.OperacaoForm;
import com.beertech.banco.infrastructure.rest.controller.form.OperacaoTransferenciaForm;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/beercoins")
public class OperacaoController {

	@Autowired
	OperacaoService operacaoService;

	@Autowired
	ContaRepository contaRepository;

	@ApiIgnore
    @PostMapping(value = "/operacao")
    public ResponseEntity<?> salvaOperacao(@Valid @RequestBody OperacaoForm operacaoForm, @ApiIgnore UriComponentsBuilder uriBuilder) {
		try {
    		Operacao operacaoNaoRealizada = new Operacao(operacaoForm.getValor(), operacaoForm.getTipo(), operacaoForm.getHash());
    		Conta conta = operacaoService.realizaOperacao(operacaoForm.getHash(), operacaoNaoRealizada);
    		URI uri = uriBuilder.path("/beercoins/conta/extrato").buildAndExpand().toUri();
    		return ResponseEntity.created(uri).body(conta);    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }
	
	@ApiIgnore
    @PostMapping(value = "/transferencia")
    public ResponseEntity<?> transferenciao(@Valid @RequestBody OperacaoTransferenciaForm operacaoForm, @ApiIgnore UriComponentsBuilder uriBuilder) {
		try {
			operacaoService.transferencia(operacaoForm.getContaOrigem(), operacaoForm.getContaDestino(), operacaoForm.getValor());
			URI uri = uriBuilder.path("/beercoins/conta/extrato").buildAndExpand().toUri();
			return ResponseEntity.created(uri).build();    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }
}
