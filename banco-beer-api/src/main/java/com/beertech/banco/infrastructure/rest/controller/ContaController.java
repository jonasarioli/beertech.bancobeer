package com.beertech.banco.infrastructure.rest.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.beertech.banco.domain.Profile;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.Operacao;
import com.beertech.banco.domain.TipoOperacao;
import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.service.BancoService;
import com.beertech.banco.infrastructure.rest.controller.dto.ContaDto;
import com.beertech.banco.infrastructure.rest.controller.dto.OperacaoDto;
import com.beertech.banco.infrastructure.rest.controller.dto.TransferenciaDto;
import com.beertech.banco.infrastructure.rest.controller.form.ContaForm;


@RestController
@RequestMapping("/conta")
public class ContaController {

	@Autowired
	BancoService bancoService; 

    @PostMapping(value = "/saque")
	public ResponseEntity saque(@Valid @RequestBody OperacaoDto operacaoDto, Principal principal) {
		Operacao operacaoNaoRealizada = new Operacao(operacaoDto.getValor(), TipoOperacao.SAQUE);
		try {
			System.out.println(principal.getName());
			//Conta conta = bancoService.realizaOperacao(operacaoDto.getHash(), operacaoNaoRealizada);
			return ResponseEntity.ok().build();
		} catch (ContaException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PostMapping(value = "/deposito")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deposito(@Valid @RequestBody OperacaoDto operacaoDto) {
		Operacao operacaoNaoRealizada = new Operacao(operacaoDto.getValor(), TipoOperacao.DEPOSITO);
		try {
			//Conta conta = bancoService.realizaOperacao(operacaoDto.getHash(), operacaoNaoRealizada);
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
    public ResponseEntity criaContaCorrente(@Valid ContaForm contaDto, UriComponentsBuilder uriBuilder) {
    	try {
    		Conta conta = new Conta(new BigDecimal(0), contaDto.getNome(),
					contaDto.getEmail(), contaDto.getCnpj(), contaDto.getSenha(), Collections.singletonList(new Profile(1L, "USUARIO")));
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
    		//bancoService.transferencia(transferenciaDto.getContaOrigem(), transferenciaDto.getContaDestino(), transferenciaDto.getValor());
    		return ResponseEntity.ok().build();    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }
    
    @GetMapping
    public ResponseEntity<?> listaContas() {
    	List<ContaDto> listaTodasAsContas = bancoService.listaTodasAsContas().stream().map(ContaDto::new).collect(Collectors.toList());    	
    	return ResponseEntity.ok(listaTodasAsContas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> contaPeloId(@PathVariable Long id) {
    	try {
    		Conta contaPeloId = bancoService.contaPeloId(id);
    		return ResponseEntity.ok(new ContaDto(contaPeloId));    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @GetMapping("/hash/{hash}")
    public ResponseEntity<?> contaPeloHash(@PathVariable String hash) {
    	try {
    		Conta contaPeloHash = bancoService.contaPeloHash(hash);
    		return ResponseEntity.ok(new ContaDto(contaPeloHash));    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @GetMapping("/extrato")
    public ResponseEntity<?> extrato(Principal principal) {
    	try {
    		Conta contaPeloId = bancoService.contaPeloEmail(principal.getName());
    		return ResponseEntity.ok(contaPeloId.getOperacoes());    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.notFound().build();
    	}
    }
}
