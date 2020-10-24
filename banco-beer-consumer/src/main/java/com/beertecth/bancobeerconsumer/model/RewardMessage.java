package com.beertecth.bancobeerconsumer.model;

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
public class RewardMessage {
    @JsonProperty(required = true, value = "tipo")
    private String type;
    @JsonProperty(required = true, value = "preco")
    private BigDecimal valor;
    @JsonProperty(required = true, value = "hash")
    private String hash;
    @JsonProperty(required = true, value = "nomeProduto")
    private String productName;

    @Override
    public String toString() {
        return "RewardMessage{" +
                "type='" + type + '\'' +
                ", valor=" + valor +
                ", hash='" + hash + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
