package com.bantads.saga.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.amqp.core.Queue;

import com.bantads.saga.models.ClienteDTO;

public class ClienteProducer {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  @Qualifier("cliente")
  private Queue queue;

  public void send(ClienteTransfer cliente) {
    System.out.println("Enviando cliente: " + cliente);
    this.template.convertAndSend(this.queue.getName(), cliente);
  }

  public ClienteTransfer sendAndReceive(ClienteDTO cliente, String action) {
    ClienteTransfer dt = new ClienteTransfer(cliente, action);
    System.out.println("Enviando e aguardando resposta para: " + cliente + " com ação: " + action);
    try {
      ClienteTransfer resposta = (ClienteTransfer) this.template.convertSendAndReceive(this.queue.getName(), dt);
      System.out.println("Resposta recebida: " + resposta);
      return resposta;
    } catch (Exception e) {
      System.err.println("Erro ao enviar/receber mensagem: " + e.getMessage());
      return null; 
    }
  }
  
  public ClienteTransfer sendAndReceiveInt(Long clienteId, String action) {
    ClienteDTO cli = new ClienteDTO();
    cli.setId(clienteId);
    ClienteTransfer dt = new ClienteTransfer(cli, action);
    System.out.println("Enviando e aguardando resposta para ID: " + clienteId + " com ação: " + action);
    ClienteTransfer resposta = (ClienteTransfer) this.template.convertSendAndReceive(this.queue.getName(), dt);
    System.out.println("Resposta recebida: " + resposta);
    return resposta;
  }
}
