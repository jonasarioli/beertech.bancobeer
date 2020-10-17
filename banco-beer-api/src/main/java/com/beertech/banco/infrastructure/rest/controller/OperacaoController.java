package com.beertech.banco.infrastructure.rest.controller;

import java.math.BigDecimal;
import java.net.URI;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.Operacao;
import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.service.BancoService;
import com.beertech.banco.infrastructure.rest.controller.dto.ContaDto;
import com.beertech.banco.infrastructure.rest.controller.dto.OperacaoDto;
import com.beertech.banco.infrastructure.rest.controller.dto.TransferenciaDto;



@RestController
@RequestMapping("/banco")
public class OperacaoController {

	@Autowired
	BancoService bancoService; 

    @PostMapping(value = "/operacao")
    public ResponseEntity salvaOperacao(@Valid @RequestBody OperacaoDto operacaoDto) {
    	Operacao operacaoNaoRealizada = new Operacao(operacaoDto.getValor(), operacaoDto.getTipo());
    	try {
    		Conta conta = bancoService.realizaOperacao(operacaoDto.getHash(), operacaoNaoRealizada);
    		return ResponseEntity.ok().build();    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }


    
    
    
    
}
