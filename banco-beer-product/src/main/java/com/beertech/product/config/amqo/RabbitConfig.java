package com.beertech.product.config.amqo;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableRabbit
public class RabbitConfig {

  @Value("${amqp.exchange}")
  private String exchangeName;

  @Value("${amqp.queue}")
  private String queueName;

  @Value("${amqp.routeKey}")
  private String routeKey;

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public TopicExchange getExchange() {
    return new TopicExchange(exchangeName, true, false);
  }

  @Bean
  public TopicExchange getDeadLetterExchange() {

    return new TopicExchange(exchangeName + "-deadLetter", true, false);
  }

  private Queue createQueue(final String queueName) {
    return QueueBuilder.durable(queueName)
        .withArgument("x-dead-letter-exchange", exchangeName + "-deadLetter")
        .build();
  }

  private Queue createDeadLetterQueue(final String queueName) {

    return QueueBuilder.durable(queueName + "-dlq").build();
  }

  @Bean
  public Declarables declarableBeans() {
    final List<Declarable> declarables = new ArrayList<>();

    final Queue queue = createQueue(queueName);
    final Queue deadLetterQueue = createDeadLetterQueue(queueName);
    final TopicExchange queueExchange = getExchange();
    final TopicExchange queueDeadLetterExchange = getDeadLetterExchange();
    final Binding queueBinding = BindingBuilder.bind(queue).to(queueExchange).with(routeKey);
    final Binding deadLetterBinding =
        BindingBuilder.bind(deadLetterQueue).to(queueDeadLetterExchange).with(routeKey);

    declarables.add(queue);
    declarables.add(queueBinding);
    declarables.add(deadLetterQueue);
    declarables.add(deadLetterBinding);

    return new Declarables(declarables);
  }
}
