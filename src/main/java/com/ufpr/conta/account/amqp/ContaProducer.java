package com.ufpr.conta.account.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.amqp.core.Queue;

public class ContaProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("conta")
    private Queue queue;

    public void send(ContaTransfer contaTransfer) {
        this.template.convertAndSend(this.queue.getName(), contaTransfer);
    }
}
