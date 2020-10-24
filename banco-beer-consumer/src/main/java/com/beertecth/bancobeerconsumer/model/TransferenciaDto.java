package com.beertecth.bancobeerconsumer.model;

import java.math.BigDecimal;

public class TransferenciaDto {
    private String contaOrigem;
    private String contaDestino;
    private BigDecimal valor;

    public TransferenciaDto(TransferenciaMessage transferenciaMessage) {
        this.contaOrigem = transferenciaMessage.getContaOrigem();
        this.contaDestino = transferenciaMessage.getContaDestino();
        this.valor = transferenciaMessage.getValor();
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }


}
