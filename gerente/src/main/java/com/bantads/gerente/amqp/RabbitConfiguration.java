package com.bantads.gerente.amqp;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bantads.gerente.amqp.GerenteTransfer;

@Configuration
public class RabbitConfiguration {

  @Bean
  @Qualifier("gerente")
  public Queue gerenteQueue() {
    System.out.println("Criando fila gerente");
    return new Queue("gerente");
  }

  @Bean
  @Qualifier("gerente-cliente")
  public Queue gerenteClienteQueue() {
    System.out.println("Criando fila gerente-cliente");
    return new Queue("gerente-cliente");
  }

  @Bean
  public GerenteReceiver receiver() {
    return new GerenteReceiver();
  }

  @Bean
  public GerenteClienteReceiver gerenteClienteReceiver() {
    return new GerenteClienteReceiver();
  }

  @Bean
  public DefaultClassMapper classMapper() {
    DefaultClassMapper classMapper = new DefaultClassMapper();
    Map<String, Class<?>> idClassMapping = new HashMap<>();
    classMapper.setTrustedPackages("*");
    idClassMapping.put("com.bantads.cliente.cliente.amqp.GerenteTransfer", GerenteTransfer.class);
    idClassMapping.put("com.bantads.saga.amqp.GerenteTransfer", GerenteTransfer.class);
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