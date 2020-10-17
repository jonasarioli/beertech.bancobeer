package com.beertech.bancobeer.relay.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  private String tipo;

  @JsonProperty(value = "valor")
  private BigDecimal valor;

  @JsonProperty(value = "hash")
  private String hash;
}
