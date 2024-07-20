package com.bantads.saga.amqp;

import com.bantads.saga.models.AccountDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AccountProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("account-saga")
    private Queue queue;

    public void send(AccountDTO account, String action) {
        System.out.println("Enviando conta: " + account + " com ação: " + action);
        this.template.convertAndSend(this.queue.getName(), account);
    }

    public AccountTransfer sendAndReceive(AccountDTO account, String action) {
        AccountTransfer dt = new AccountTransfer(account, action);
        System.out.println("Enviando e aguardando resposta para: " + account + " com ação: " + action);
        try {
            AccountTransfer resposta = (AccountTransfer) this.template.convertSendAndReceive(this.queue.getName(), dt);
            System.out.println("Resposta recebida: " + resposta);
            return resposta;
        } catch (Exception e) {
            System.err.println("Erro ao enviar/receber mensagem: " + e.getMessage());
            return null; // Ou considere lançar uma exceção personalizada
        }
    }
}
