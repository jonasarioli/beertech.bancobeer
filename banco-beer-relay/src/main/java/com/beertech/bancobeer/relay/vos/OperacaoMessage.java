package com.beertech.bancobeer.relay.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacaoMessage {
  @JsonProperty(value = "tipo")
  @ApiParam(example = "SAQUE")
  private String tipo;

  @JsonProperty(value = "valor")
  @ApiParam(example = "12.5")
  private BigDecimal valor;

  @JsonProperty(value = "hash")
  @ApiParam(example = "123456")
  private String hash;
}
