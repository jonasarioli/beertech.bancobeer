package com.beertech.product.model.amqp;

import com.beertech.product.model.EType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RewardMessage implements Serializable {
    @JsonProperty(value = "tipo")
    private String type;

    @JsonProperty(value = "preco")
    private BigDecimal productPrice;

    @JsonProperty(value = "hash")
    private String hash;

    @JsonProperty(value = "nomeProduto")
    private String productName;

    public RewardMessage() {
        this.type = EType.RESGATE.name();
    }

}
