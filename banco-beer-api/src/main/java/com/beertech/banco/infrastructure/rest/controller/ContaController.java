package com.beertech.banco.infrastructure.rest.controller;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.Operacao;
import com.beertech.banco.domain.TipoOperacao;
import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.service.BancoService;
import com.beertech.banco.infrastructure.rest.controller.dto.ContaDto;
import com.beertech.banco.infrastructure.rest.controller.dto.OperacaoDto;
import com.beertech.banco.infrastructure.rest.controller.dto.TransferenciaDto;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;


@RestController
@RequestMapping("/conta")
public class ContaController {

	@Autowired
	BancoService bancoService; 

    @PostMapping(value = "/saque")
	public ResponseEntity saque(@Valid @RequestBody OperacaoDto operacaoDto) {
		Operacao operacaoNaoRealizada = new Operacao(operacaoDto.getValor(), TipoOperacao.SAQUE);
		try {
			Conta conta = bancoService.realizaOperacao(operacaoDto.getHash(), operacaoNaoRealizada);
			return ResponseEntity.ok().build();
		} catch (ContaException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PostMapping(value = "/deposito")
	public ResponseEntity deposito(@Valid @RequestBody OperacaoDto operacaoDto) {
		Operacao operacaoNaoRealizada = new Operacao(operacaoDto.getValor(), TipoOperacao.DEPOSITO);
		try {
			Conta conta = bancoService.realizaOperacao(operacaoDto.getHash(), operacaoNaoRealizada);
			return ResponseEntity.ok().build();
		} catch (ContaException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

    @GetMapping(value = "/saldo/{hash}")
    public ResponseEntity getDataSaldo(@PathVariable String hash) throws JSONException {
    	try {
    		BigDecimal saldo = bancoService.saldo(hash);
    		return ResponseEntity.ok(saldo);    		
    	} catch(ContaException ex) {
    		ex.printStackTrace();
    		return ResponseEntity.notFound().build();
    	}
    }

    @PostMapping("/cadastro")
    public ResponseEntity criaContaCorrente(@Valid ContaDto contaDto, UriComponentsBuilder uriBuilder) {
    	try {
    		Conta conta = new Conta(contaDto.getHash());
    		conta = bancoService.criarConta(conta);
    		URI uri = uriBuilder.path("/saldo/{hash}").buildAndExpand(conta.getHash()).toUri();
    		return ResponseEntity.created(uri).body(conta);
    	} catch (ContaException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }
    
    @PostMapping("/transferencia")
    public ResponseEntity transferencia(@Valid @RequestBody TransferenciaDto transferenciaDto) {
    	try {
    		bancoService.transferencia(transferenciaDto.getContaOrigem(), transferenciaDto.getContaDestino(), transferenciaDto.getValor());
    		return ResponseEntity.ok().build();    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }
    
    
    
    
}
