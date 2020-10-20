package com.beertech.banco.infrastructure.rest.controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.Operacao;
import com.beertech.banco.domain.TipoOperacao;
import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.service.ContaService;
import com.beertech.banco.infrastructure.rest.controller.dto.ContaDto;
import com.beertech.banco.infrastructure.rest.controller.dto.OperacaoDto;
import com.beertech.banco.infrastructure.rest.controller.dto.SaldoDto;
import com.beertech.banco.infrastructure.rest.controller.dto.TransferenciaForm;
import com.beertech.banco.infrastructure.rest.controller.form.ContaForm;
import com.beertech.banco.infrastructure.rest.controller.form.DepositoForm;
import com.beertech.banco.infrastructure.rest.controller.form.SaqueForm;


@RestController
@RequestMapping("/beercoins/conta")
public class ContaController {

	@Autowired
	ContaService contaService; 

    @PostMapping(value = "/saque")
	public ResponseEntity<?> saque(@Valid @RequestBody SaqueForm operacaoDto, Principal principal) {
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
	public ResponseEntity<?> deposito(@Valid @RequestBody DepositoForm operacaoDto) {
		Operacao operacaoNaoRealizada = new Operacao(operacaoDto.getValor(), TipoOperacao.DEPOSITO);
		try {
			//Conta conta = bancoService.realizaOperacao(operacaoDto.getHash(), operacaoNaoRealizada);
			return ResponseEntity.ok().build();
		} catch (ContaException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

    @GetMapping(value = "/saldo")
    public ResponseEntity<?> getDataSaldo(Principal principal) throws JSONException {
    	try {
    		Conta contaPeloEmail = contaService.contaPeloEmail(principal.getName());
    		return ResponseEntity.ok(new SaldoDto(contaPeloEmail.getSaldo()));    		
    	} catch(ContaException ex) {
    		ex.printStackTrace();
    		return ResponseEntity.notFound().build();
    	}
    }

    @PostMapping()
    public ResponseEntity<?> criaConta(@Valid ContaForm contaForm, UriComponentsBuilder uriBuilder) {
    	try {    		
    		Conta conta = new Conta(contaForm);    		
    		conta = contaService.criarConta(conta);
    		URI uri = uriBuilder.path("/conta/{id}").buildAndExpand(conta.getHash()).toUri();
    		return ResponseEntity.created(uri).body(conta);
    	} catch (ContaException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }
    
    @PostMapping("/transferencia")
    public ResponseEntity<?> transferencia(@Valid @RequestBody TransferenciaForm transferenciaDto) {
    	try {
    		//bancoService.transferencia(transferenciaDto.getContaOrigem(), transferenciaDto.getContaDestino(), transferenciaDto.getValor());
    		return ResponseEntity.ok().build();    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.badRequest().body(ex.getMessage());
    	}
    }
    
    @GetMapping
    public ResponseEntity<?> listaContas() {
    	List<ContaDto> listaTodasAsContas = contaService.listaTodasAsContas().stream().map(ContaDto::new).collect(Collectors.toList());    	
    	return ResponseEntity.ok(listaTodasAsContas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> contaPeloId(@PathVariable Long id) {
    	try {
    		Conta contaPeloId = contaService.contaPeloId(id);
    		return ResponseEntity.ok(new ContaDto(contaPeloId));    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @GetMapping("/hash/{hash}")
    public ResponseEntity<?> contaPeloHash(@PathVariable String hash) {
    	try {
    		Conta contaPeloHash = contaService.contaPeloHash(hash);
    		return ResponseEntity.ok(new ContaDto(contaPeloHash));    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @GetMapping("/extrato")
    public ResponseEntity<?> extrato(Principal principal) {
    	try {
    		Conta contaPeloId = contaService.contaPeloEmail(principal.getName());
    		return ResponseEntity.ok(contaPeloId.getOperacoes().stream().map(OperacaoDto::new).collect(Collectors.toList()));    		
    	} catch (ContaException | IllegalArgumentException ex) {
    		return ResponseEntity.notFound().build();
    	}
    }
}
