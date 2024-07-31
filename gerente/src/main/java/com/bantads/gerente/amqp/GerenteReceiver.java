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

@RabbitListener(queues = "gerente")
public class GerenteReceiver {

  @Autowired
  private GerenteRepository repo;

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private ModelMapper mapper;

  @RabbitHandler
  public GerenteTransfer receive(@Payload GerenteTransfer gerenteTransfer) {
    // System.out.println("Recebido: " + gerenteTransfer.getGerente().getId());
    if (gerenteTransfer.getAction().equals("create-gerente")) {
      System.out.println("Criando um Gerente " + gerenteTransfer.getGerente().getNome());
      try {

        GerenteDTO gerente = gerenteTransfer.getGerente();

        Gerente gerenteModel = mapper.map(gerente, Gerente.class);

        repo.save(gerenteModel);

        gerenteTransfer.setAction("gerente-ok");
      } catch (Exception e) {
        gerenteTransfer.setAction("gerente-failed");
        System.out.println("Erro ao criar gerente");
        System.out.println(e);

        return null;
      }

      System.out.println("Gerente criado com sucesso!");

      return gerenteTransfer;

    } else if (gerenteTransfer.getAction().equals("remove-gerente")) {
      System.out.println("Removendo Gerente");

      GerenteDTO gerente = gerenteTransfer.getGerente();
      Gerente gerenteModel = repo.findById(gerente.getId()).get();
      if (gerenteModel != null) {
        repo.delete(gerenteModel);

        gerenteTransfer.setAction("gerente-deleted");
        // this.template.convertAndSend("saga", gerenteTransfer);
        return gerenteTransfer;
      }
    } else if (gerenteTransfer.getAction().equals("deny-cliente")) {
      System.out.println("Reprovando cliente");
      GerenteDTO gerente = gerenteTransfer.getGerente();
      Gerente g = repo.findById(gerente.getId()).get();
      try {
        g.setNumClientes(g.getNumClientes() - 1);
        repo.save(g);
      } catch (Exception e) {
        gerenteTransfer.setAction("gerente-failed");
        System.out.println(e.getLocalizedMessage());
        return null;
      }
      return gerenteTransfer;
    }
    System.out.println("Ação recebida não reconhecida: " + gerenteTransfer.getAction());

    return null;
  }
}