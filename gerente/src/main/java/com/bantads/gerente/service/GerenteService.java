package com.bantads.gerente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.repository.GerenteRepository;

@Service
public class GerenteService {
    @Autowired
    private GerenteRepository gerenteRepository;

    public List<Gerente> listarTodos() {
        return gerenteRepository.findAll();
    }

    public Gerente inserirGerente(Gerente gerente) {
        return gerenteRepository.save(gerente);
    }

    public void removerGerente(Long id) {
        gerenteRepository.deleteById(id);
    }

    public Gerente alterarGerente(Long id, Gerente gerente) {
        Optional<Gerente> gerenteExistente = gerenteRepository.findById(id);
        if (gerenteExistente.isPresent()) {
            Gerente g = gerenteExistente.get();
            g.setNome(gerente.getNome());
            g.setEmail(gerente.getEmail());
            g.setTelefone(gerente.getTelefone());
            return gerenteRepository.save(g);
        }
        return null;
    }

    public Gerente consultarGerenteComMenosContas() {
        return gerenteRepository.findTopByOrderByContasAsc().orElse(null);
    }

    public Gerente consultarGerenteComMaisContas() {
        return gerenteRepository.findTopByOrderByContasDesc().orElse(null);
    }
}
