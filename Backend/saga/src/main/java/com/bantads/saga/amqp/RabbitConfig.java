package com.bantads.saga.amqp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    @Qualifier("cliente")
    public Queue clientQueue() {
        System.out.println("Criando fila cliente");
        return new Queue("cliente");
    }

    @Bean
    @Qualifier("gerente")
    public Queue gerenteQueue() {
        System.out.println("Criando fila gerente");
        return new Queue("gerente");
    }

    @Bean
    @Qualifier("account-saga")
    public Queue accountQueue() {
        System.out.println("Criando fila account");
        return new Queue("account-saga");
    }

    @Bean
    @Qualifier("auth")
    public Queue authQueue() {
        System.out.println("Criando fila auth");
        return new Queue("auth");
    }

    @Bean
    public ClienteProducer clienteSender() {
        return new ClienteProducer();
    }

    @Bean
    public GerenteProducer gerenteSender() {
        return new GerenteProducer();
    }

    @Bean
    public AccountProducer accountSender() {
        return new AccountProducer();
    }

    @Bean
    public AuthProducer authSender() {
        return new AuthProducer();
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        classMapper.setTrustedPackages("*");
        idClassMapping.put("com.bantads.gerente.amqp.GerenteTransfer", GerenteTransfer.class);
        idClassMapping.put("com.bantads.account.account.amqp.AccountTransfer", AccountTransfer.class);
        idClassMapping.put("com.bantads.auth.auth.amqp.AuthTransfer", AuthTransfer.class);
        idClassMapping.put("com.bantads.cliente.cliente.amqp.ClienteTransfer", ClienteTransfer.class);
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
