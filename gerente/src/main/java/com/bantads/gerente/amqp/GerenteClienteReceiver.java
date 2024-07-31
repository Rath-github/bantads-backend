package com.bantads.gerente.amqp;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.Payload;

import com.bantads.gerente.utils.ValidarCpf;
import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.model.GerenteDTO;
import com.bantads.gerente.repository.GerenteRepository;

// import com.bantads.auth.auth.serializers.BasicClienteDTO;

@RabbitListener(queues = "gerente-cliente")
public class GerenteClienteReceiver {

  @Autowired
  private GerenteRepository repo;

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private ModelMapper mapper;

  @RabbitHandler
  public void receive(@Payload GerenteTransfer gerenteTransfer) {
    System.out.println("Gerente cliente recebido");
    if (gerenteTransfer.getAction().equals("create-cliente")) {
      System.out.println("Recebendo cliente");
      Gerente gerente = repo.findFirstByOrderByNumClientes();

      System.out.println("Gerente " + gerente.getId());
      gerente.setNumClientes(gerente.getNumClientes() + 1);

      repo.save(gerente);

      ModelMapper modelMapper = new ModelMapper();
      GerenteDTO gerenteDTO = modelMapper.map(gerente, GerenteDTO.class);

      gerenteTransfer.setAction("set-gerente");
      System.out.println("Enviando gerente " + gerente.getId());

      gerenteTransfer.setGerente(gerenteDTO);
      this.template.convertAndSend("gerente-cliente", gerenteTransfer);
      // return null;
    }
    System.out.println("Ação recebida não reconhecida: " + gerenteTransfer.getAction());

    // return null;
  }
}