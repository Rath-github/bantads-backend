package com.bantads.gerente.saga;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.service.GerenteService;
import com.bantads.gerente.repository.GerenteRepository;

@Service
public class AdminSaga {

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private GerenteRepository gerenteRepository;

    private static final String EXCHANGE_NAME = "gerenteExchange";

    @Transactional
    public void inserirGerente(Gerente gerente) {
        Gerente savedGerente = gerenteService.inserirGerente(gerente);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "gerente.insert", savedGerente);
    }

    @Transactional
    public void removerGerente(Long id) {
        gerenteService.removerGerente(id);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "gerente.delete", id);
    }

    public Gerente visualizarGerente(Long id) {
        return gerenteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Gerente editarGerente(Long id, Gerente gerenteDetails) {
        Gerente existingGerente = gerenteRepository.findById(id).orElse(null);
        if (existingGerente != null) {
            existingGerente.setNome(gerenteDetails.getNome());
            existingGerente.setEmail(gerenteDetails.getEmail());
            existingGerente.setCpf(gerenteDetails.getCpf());
            existingGerente.setTelefone(gerenteDetails.getTelefone());
            Gerente updatedGerente = gerenteRepository.save(existingGerente);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "gerente.update", updatedGerente);
            return updatedGerente;
        }
        return null;
    }
}