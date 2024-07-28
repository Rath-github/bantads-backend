package com.bantads.gerente.config;

import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.service.GerenteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String QUEUE_NAME = "gerenteQueue";
    public static final String EXCHANGE_NAME = "gerenteExchange";

    @Autowired
    private GerenteService gerenteService;

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue());
        container.setMessageListener(new ChannelAwareMessageListener() {

            @Override
            public void onMessage(org.springframework.amqp.core.Message message, com.rabbitmq.client.Channel channel) throws Exception {
                try {
                    // A mensagem está sendo convertida para uma String:
                    String messageBody = new String(message.getBody(), "UTF-8");
                    
                    // A mensagem é deserializada para um objeto Gerente:
                    ObjectMapper objectMapper = new ObjectMapper();
                    Gerente gerente = objectMapper.readValue(messageBody, Gerente.class);

                    String routingKey = message.getMessageProperties().getReceivedRoutingKey();
                    if (routingKey.equals("gerente.insert")) {
                        gerenteService.inserirGerente(gerente);
                    } else if (routingKey.equals("gerente.delete")) {
                        gerenteService.removerGerente(gerente.getId());
                    } else if (routingKey.equals("gerente.update")) {
                        gerenteService.alterarGerente(gerente.getId(), gerente);
                    }

                    // Caso o processamento seja bem-sucedido:
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    System.err.println("Erro ao processar mensagem: " + e.getMessage());

                    // Enviar mensagem para uma fila de erros ou rejeitar a mensagem:
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            }
        });
        return container;
    }
}