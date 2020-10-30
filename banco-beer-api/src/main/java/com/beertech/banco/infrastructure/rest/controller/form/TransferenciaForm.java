package com.beertech.banco.infrastructure.rest.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransferenciaForm {

    @NotBlank
    private String contaDestino;
    @NotNull
    @Min(value = 1, message = "Valor deve ser maior do que 0!")
    private BigDecimal valor;

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
