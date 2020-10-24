package com.beertech.product.service.amqp;

import com.beertech.product.model.amqp.RewardMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;

class MessageSenderTest {

    @Value("${amqp.exchange}")
    private String exchangeName;

    @Value("${amqp.routeKey}")
    private String routeKey;

    private MessageSender subject;
    private RabbitTemplate rabbitTemplateMock;

    @BeforeEach
    void setUp() {
        this.rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
        this.subject = new MessageSender(this.rabbitTemplateMock);
    }

    @Test
    public void sendRewardMessage() {
        RewardMessage message = new RewardMessage();
        assertThatCode(() -> this.subject.sendRewardMessage(message)).doesNotThrowAnyException();
        Mockito.verify(this.rabbitTemplateMock)
                .convertAndSend(eq(exchangeName), eq(routeKey), eq(message));
    }
}