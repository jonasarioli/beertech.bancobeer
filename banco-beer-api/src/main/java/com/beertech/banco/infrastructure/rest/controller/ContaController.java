package com.beertech.banco.infrastructure.rest.controller;

import java.net.URI;
import java.security.Principal;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.EPerfil;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;
import com.beertech.banco.domain.service.ContaService;
import com.beertech.banco.domain.service.OperacaoService;
import com.beertech.banco.infrastructure.amqp.model.OperacaoMessage;
import com.beertech.banco.infrastructure.amqp.model.TransferenciaMessage;
import com.beertech.banco.infrastructure.amqp.service.RelayService;
import com.beertech.banco.infrastructure.rest.controller.dto.ContaDto;
import com.beertech.banco.infrastructure.rest.controller.dto.OperacaoDto;
import com.beertech.banco.infrastructure.rest.controller.dto.SaldoDto;
import com.beertech.banco.infrastructure.rest.controller.form.ContaForm;
import com.beertech.banco.infrastructure.rest.controller.form.DepositoForm;
import com.beertech.banco.infrastructure.rest.controller.form.SaqueForm;
import com.beertech.banco.infrastructure.rest.controller.form.TransferenciaForm;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/beercoins/conta")
@CrossOrigin
public class ContaController {

    @Autowired
    ContaService contaService;

    @Autowired
    OperacaoService operacaoService;

    @Autowired
    private RelayService relayService;

    @PostMapping(value = "/saque")
    public ResponseEntity<?> saque(@Valid @RequestBody SaqueForm saque, @ApiIgnore Principal principal) {
        try {
            Conta contaPeloEmail = contaService.contaPeloEmail(principal.getName());
            OperacaoMessage message = new OperacaoMessage(TipoOperacao.SAQUE.name(), saque.getValor(), contaPeloEmail.getHash());
            relayService.operation(message);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ContaException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(value = "/deposito")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deposito(@Valid @RequestBody DepositoForm depositoForm) {
        try {
            OperacaoMessage message = new OperacaoMessage(TipoOperacao.DEPOSITO.name(), depositoForm.getValor(), depositoForm.getHashDaConta());
            relayService.operation(message);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ContaException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(value = "/saldo")
    public ResponseEntity<SaldoDto> getDataSaldo(@ApiIgnore Principal principal) throws JSONException {
        try {
            Conta contaPeloEmail = contaService.contaPeloEmail(principal.getName());
            return ResponseEntity.ok(new SaldoDto(contaPeloEmail.getSaldo()));
        } catch (ContaException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Conta> criaConta(@Valid ContaForm contaForm, @ApiIgnore UriComponentsBuilder uriBuilder) {
        try {
            Conta conta = new Conta(contaForm);
            conta = contaService.criarConta(conta, EPerfil.USER);
            URI uri = uriBuilder.path("/beercoins/conta/{id}").buildAndExpand(conta.getId()).toUri();
            return ResponseEntity.created(uri).body(conta);
        } catch (ContaException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<?> transferencia(@Valid @RequestBody TransferenciaForm transferenciaForm, @ApiIgnore Principal principal) {
        try {
            Conta contaPeloEmail = contaService.contaPeloEmail(principal.getName());
            TransferenciaMessage message = new TransferenciaMessage(TipoOperacao.TRANSFERENCIA.name(), contaPeloEmail.getHash(), transferenciaForm.getValor(), transferenciaForm.getContaDestino());
            relayService.transfer(message);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ContaException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Pagina a ser carregada", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Quantidade de registros", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Ordenacao dos registros")
    })
    @GetMapping
    public ResponseEntity<Page<ContaDto>> listaContas(@PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 10) @ApiIgnore Pageable pageable) {
        Page<Conta> listaTodasAsContasUsuarios = contaService.listaTodasAsContasUsuarios(pageable);
        return ResponseEntity.ok(ContaDto.convert(listaTodasAsContasUsuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDto> contaPeloId(@PathVariable Long id) {
        try {
            Conta contaPeloId = contaService.contaPeloId(id);
            return ResponseEntity.ok(new ContaDto(contaPeloId));
        } catch (ContaException | IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hash/{hash}")
    public ResponseEntity<ContaDto> contaPeloHash(@PathVariable String hash) {
        try {
            Conta contaPeloHash = contaService.contaPeloHash(hash);
            return ResponseEntity.ok(new ContaDto(contaPeloHash));
        } catch (ContaException | IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Pagina a ser carregada", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Quantidade de registros", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Ordenacao dos registros")
    })
    @GetMapping("/extrato")
    public ResponseEntity<Page<OperacaoDto>> extrato(@RequestParam(required = false) String tipoOperacao, @ApiIgnore Principal principal,
                                                     @PageableDefault(sort = "dataHora", direction = Direction.DESC, page = 0, size = 10) @ApiIgnore Pageable pageable) {
        try {
            Conta contaPeloId = contaService.contaPeloEmail(principal.getName());
            Page<Operacao> operacoes;
            if (tipoOperacao == null)
                operacoes = operacaoService.extrato(contaPeloId.getId(), pageable);
            else
                operacoes = operacaoService.extratoPorTipo(contaPeloId.getId(), TipoOperacao.valueOf(tipoOperacao), pageable);
            return ResponseEntity.ok(OperacaoDto.converter(operacoes));
        } catch (ContaException | IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiIgnore
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criaAdmin(@Valid ContaForm contaForm, UriComponentsBuilder uriBuilder) {
        try {
            Conta conta = new Conta(contaForm);
            conta = contaService.criarConta(conta, EPerfil.ADMIN);
            URI uri = uriBuilder.path("/conta/{id}").buildAndExpand(conta.getHash()).toUri();
            return ResponseEntity.created(uri).body(conta);
        } catch (ContaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
