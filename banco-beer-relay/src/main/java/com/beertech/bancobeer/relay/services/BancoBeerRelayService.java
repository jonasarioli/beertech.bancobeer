package com.beertech.bancobeer.relay.services;

import com.beertech.bancobeer.relay.senders.BancoBeerMessageSender;
import com.beertech.bancobeer.relay.vos.OperacaoMessage;
import com.beertech.bancobeer.relay.vos.TransferenciaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BancoBeerRelayService {

    private final BancoBeerMessageSender messageSender;

    @Autowired
    public BancoBeerRelayService(BancoBeerMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void transfer(TransferenciaMessage transferenciaMessage) {
        transferenciaMessage.setTipo("TRANSFERENCIA");
        messageSender.sendTransferMessage(transferenciaMessage);
    }

    public void operation(OperacaoMessage operacaoMessage) {
        messageSender.sendOperationMessage(operacaoMessage);
    }
}
