package com.ufpr.conta.account.amqp.saga;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {


    private static final String SAGA_QUEUE_NAME = "account-saga";


    private static final String SAGA_EXCHANGE_NAME = "saga-exchange";

  
    @Bean
    public Queue sagaQueue() {
        return new Queue(SAGA_QUEUE_NAME, true);
    }

   
    @Bean
    public DirectExchange sagaExchange() {
        return new DirectExchange(SAGA_EXCHANGE_NAME);
    }

   
    @Bean
    public Binding bindSagaQueue(Queue sagaQueue, DirectExchange sagaExchange) {
        return BindingBuilder.bind(sagaQueue).to(sagaExchange).with(SAGA_QUEUE_NAME);
    }
}
