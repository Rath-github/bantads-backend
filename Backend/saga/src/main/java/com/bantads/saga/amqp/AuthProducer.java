package com.bantads.saga.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.bantads.saga.models.LoginDTO;
import org.springframework.amqp.core.Queue;

public class AuthProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("auth")
    private Queue queue;

    public void send(AuthTransfer auth) {
        this.template.convertAndSend(this.queue.getName(), auth);
    }

    public AuthTransfer sendAndReceive(LoginDTO auth, String action) {
        AuthTransfer dt = new AuthTransfer(auth, action);
        System.out.println("Chegou no send and receive AUTH");
        System.out.println(dt.getLogin());
        AuthTransfer resposta = (AuthTransfer) this.template.convertSendAndReceive(this.queue.getName(), dt);
        System.out.println("Resposta: " + resposta);
        return resposta;
    }
}
