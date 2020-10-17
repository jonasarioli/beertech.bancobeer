package com.beertech.bancobeer.relay.controllers;

import com.beertech.bancobeer.relay.services.BancoBeerRelayService;
import com.beertech.bancobeer.relay.vos.OperacaoMessage;
import com.beertech.bancobeer.relay.vos.TransferenciaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class BancoBeerRelayController {

  @Autowired BancoBeerRelayService relayService;

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/transferencia")
  public ResponseEntity<Void> transferBalance(@RequestBody TransferenciaMessage message) {
    relayService.transfer(message);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/deposito")
  public ResponseEntity<Void> creditAccount(@RequestBody OperacaoMessage message) {
    message.setTipo("DEPOSITO");
    relayService.operation(message);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/saque")
  public ResponseEntity<Void> debitAccount(@RequestBody OperacaoMessage message) {
    message.setTipo("SAQUE");
    relayService.operation(message);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
