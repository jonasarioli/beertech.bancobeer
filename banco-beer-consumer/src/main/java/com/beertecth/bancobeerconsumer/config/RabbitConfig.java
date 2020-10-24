package com.beertecth.bancobeerconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String CONTA_QUEUE = "conta-corrente";
    public static final String CONTA_EXCHANGE_NAME = "contas-exchange";
    public static final String CONTA_ROUTING_KEY = "operacao";

    public static final String REWARD_QUEUE = "reward";
    public static final String REWARD_EXCHANGE_NAME = "reward-exchange";
    public static final String REWARD_ROUTING_KEY = "compra";

    @Bean
    public TopicExchange getContaExchange() {
        return new TopicExchange(CONTA_EXCHANGE_NAME, true, false);
    }

    @Bean
    public TopicExchange geRewardExchange() {
        return new TopicExchange(REWARD_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue declareContaQueue() {
        return QueueBuilder.durable(CONTA_QUEUE)
                .build();
    }

    private Queue declareRewardQueue(final String queueName) {
        return QueueBuilder.durable(queueName)
                .build();
    }

    @Bean
    public Declarables declarableBeans() {
        final List<Declarable> declarables = new ArrayList<>();

        final Queue contaQueue = declareRewardQueue(CONTA_QUEUE);
        final Queue rewardQueue = declareRewardQueue(REWARD_QUEUE);
        final TopicExchange queueContaExchange = getContaExchange();
        final TopicExchange queueRewardExchange = geRewardExchange();
        final Binding queueContaBinding = BindingBuilder.bind(contaQueue).to(queueContaExchange).with(CONTA_ROUTING_KEY);
        final Binding queueRewardBinding =
                BindingBuilder.bind(rewardQueue).to(queueRewardExchange).with(REWARD_ROUTING_KEY);

        declarables.add(contaQueue);
        declarables.add(queueContaBinding);
        declarables.add(rewardQueue);
        declarables.add(queueRewardBinding);

        return new Declarables(declarables);
    }
}
