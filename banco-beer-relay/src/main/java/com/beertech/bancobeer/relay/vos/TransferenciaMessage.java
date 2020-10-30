package com.beertech.bancobeer.relay.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class TransferenciaMessage {

    @JsonProperty(value = "tipo")
    @ApiModelProperty(example = "TRANSFERENCIA")
    private String tipo;

    @JsonProperty(value = "origem")
    @ApiModelProperty(example = "123456")
    private String contaOrigem;

    @JsonProperty(value = "valor")
    @ApiModelProperty(example = "45.15")
    private BigDecimal valor;

    @JsonProperty(value = "destino")
    @ApiModelProperty(example = "654321")
    private String contaDestino;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }
}
