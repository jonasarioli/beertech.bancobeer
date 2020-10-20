package com.beertech.banco.infrastructure.rest.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.service.ContaService;
import com.beertech.banco.infrastructure.rest.controller.form.OperacaoForm;
import com.beertech.banco.infrastructure.rest.controller.form.OperacaoTransferenciaForm;
import com.beertech.banco.infrastructure.rest.controller.form.TransferenciaForm;

import springfox.documentation.annotations.ApiIgnore;



@RestController
@RequestMapping("/beercoins")
public class OperacaoController {

	@Autowired
	ContaService contaService; 

	@ApiIgnore
    @PostMapping(value = "/operacao")
    public ResponseEntity<?> salvaOperacao(@Valid @RequestBody OperacaoForm operacaoForm) {
		try {
    		Operacao operacaoNaoRealizada = new Operacao(operacaoForm.getValor(), operacaoForm.getTipo());
    		Conta conta = contaService.realizaOperacao(operacaoForm.getHash(), operacaoNaoRealizada);
    		return ResponseEntity.ok().build();    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }
	
	@ApiIgnore
    @PostMapping(value = "/transferencia")
    public ResponseEntity<?> transferenciao(@Valid @RequestBody OperacaoTransferenciaForm operacaoForm) {
		try {
    		contaService.transferencia(operacaoForm.getContaOrigem(), operacaoForm.getContaDestino(), operacaoForm.getValor());
    		return ResponseEntity.ok().build();    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }


    
    
    
    
}
