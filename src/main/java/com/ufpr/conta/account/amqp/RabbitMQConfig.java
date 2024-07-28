package com.ufpr.conta.account.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    @Bean
    @Qualifier("conta")
    public Queue contaQueue() { return new Queue("conta"); }

    @Bean
    public ContaProducer contaProducer() { return new ContaProducer(); }

    @Bean
    public ContaReceiver contaReceiver() { return new ContaReceiver(); }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        classMapper.setTrustedPackages("*");

        idClassMapping.put("com.api.saga.amqp.ContaTransfer", ContaTransfer.class);

        classMapper.setIdClassMapping(idClassMapping);

        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());

        return converter;
    }
}
