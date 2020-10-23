package com.beertech.products.model.amqp;

import com.beertech.products.model.EType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RewardMessage {
    @JsonProperty(value = "tipo")
    private String type;

    @JsonProperty(value = "preco")
    private BigDecimal productPrice;

    @JsonProperty(value = "hash")
    private String hash;

    @JsonProperty(value = "productName")
    private String productName;

    public RewardMessage() {
        this.type = EType.RESGATE.name();
    }

}
