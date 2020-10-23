package com.beertech.banco.infrastructure.rest.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SaqueForm {

    @NotNull
    @Min(value = 0, message = "Valor deve ser maior do que 0!")
    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
