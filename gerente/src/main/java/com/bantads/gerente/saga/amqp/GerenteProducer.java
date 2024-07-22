package com.bantads.gerente.saga.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerenteProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarParaAutocadastroQueue(NovaContaRequest novaContaRequest) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.AUTOCADASTRO_QUEUE, novaContaRequest);
    }

    public void enviarParaRemocaoQueue(Long gerenteId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.REMOCAO_QUEUE, gerenteId);
    }

    public void enviarParaInsercaoQueue(Gerente novoGerente) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.INSERCAO_QUEUE, novoGerente);
    }
}

