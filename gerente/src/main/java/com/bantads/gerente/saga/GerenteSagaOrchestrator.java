package com.bantads.gerente.saga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bantads.gerente.model.Gerente;
import com.bantads.conta.model.Conta;
import com.bantads.gerente.service.GerenteService;
import com.bantads.conta.service.ContaService;

@Service
public class GerenteSagaOrchestrator {

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private ContaService contaService;

    @Transactional
    public void autocadastroGerente(NovaContaRequest novaContaRequest) {
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

    @Transactional
    public void removerGerente(Long gerenteId) {
        Gerente gerente = gerenteService.consultarGerenteComMenosContas();
        if (gerente == null) {
            throw new RuntimeException("Nenhum gerente disponível para remoção.");
        }

        gerenteService.removerGerente(gerente.getId());
    }

    @Transactional
    public void inserirGerente(Gerente novoGerente) {
        gerenteService.inserirGerente(novoGerente);
    }
}
