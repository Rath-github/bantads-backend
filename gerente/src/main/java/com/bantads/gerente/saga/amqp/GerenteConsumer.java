package com.bantads.gerente.saga.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerenteConsumer {

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private ContaService contaService;

    @RabbitListener(queues = RabbitMQConfig.AUTOCADASTRO_QUEUE)
    public void processarAutocadastro(NovaContaRequest novaContaRequest) {
        Gerente gerente = gerenteService.consultarGerenteComMenosContas();
        if (gerente == null) {
            throw new RuntimeException("Nenhum gerente disponível para assumir a nova conta.");
        }

        Conta conta = new Conta();
        conta.setNumero(novaContaRequest.getNumero());
        conta.setSaldo(novaContaRequest.getSaldo());
        conta.setGerente(gerente);
        contaService.criarConta(conta);

        gerente.getContas().add(conta);
        gerenteService.alterarGerente(gerente.getId(), gerente);
    }

    @RabbitListener(queues = RabbitMQConfig.REMOCAO_QUEUE)
    public void processarRemocao(Long gerenteId) {
        Gerente gerente = gerenteService.consultarGerenteComMenosContas();
        if (gerente == null) {
            throw new RuntimeException("Nenhum gerente disponível para remoção.");
        }

        gerenteService.removerGerente(gerente.getId());
    }

    @RabbitListener(queues = RabbitMQConfig.INSERCAO_QUEUE)
    public void processarInsercao(Gerente novoGerente) {
        gerenteService.inserirGerente(novoGerente);
    }
}

