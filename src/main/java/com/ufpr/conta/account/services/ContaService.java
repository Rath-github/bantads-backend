package com.ufpr.conta.account.services;

import com.ufpr.conta.account.repositories.ContaRepository;
import com.ufpr.conta.account.models.ContaModel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService {
    final ContaRepository contaRepository;

    @Transactional
    public ContaModel save(ContaModel contaModel) { return contaRepository.save(contaModel); }

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Optional<ContaModel> findById(UUID id) { return contaRepository.findById(id); }

    public Optional<ContaModel> findByIdCliente(UUID idCliente) { return contaRepository.findByIdCliente(idCliente); }

    public List<ContaModel> findByIdGerente(UUID idGerente) { return contaRepository.findByIdGerente(idGerente); }

    public List<ContaModel> findByIdGerenteAndAtivo(UUID idGerente, Boolean ativo) {
        return contaRepository.findByIdGerenteAndAtivo(idGerente, ativo);
    }

    public Optional<ContaModel> findByIdGerenteSaga(UUID idGerenteAntigo) {
        return contaRepository.findByIdGerenteSaga(idGerenteAntigo);
    }

    @Transactional
    public void delete(ContaModel contaModel) {
        contaRepository.delete(contaModel);
    }

    public boolean existsByIdCliente(UUID idCliente) { return contaRepository.existsByIdCliente(idCliente); }

    public List<ContaModel> findByIdGerenteMelhores(UUID idGerente) { return contaRepository.findByIdGerenteMelhores(idGerente); }
}
