package com.bantads.gerente.saga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bantads.gerente.service.GerenteService;
import com.bantads.gerente.model.Gerente;

@Service
public class AdminSaga {

    @Autowired
    private GerenteService gerenteService;

    public void inserirAdmin(Gerente gerente) {
        gerenteRepository.save(gerente);
        // Implementar depois: Enviar mensagem para o RabbitMQ.
    }

    public void removerAdmin(Long id) {
        gerenteRepository.deleteById(id);
        // Implementar depois: Enviar mensagem para o RabbitMQ.
    }

    public Optional<Gerente> visualizarAdmin(Long id) {
        return gerenteRepository.findById(id);
    }

    public Gerente editarAdmin(Long id, Gerente adminDetails) {
        Optional<Gerente> optionalAdmin = gerenteRepository.findById(id);

        if (optionalAdmin.isPresent()) {
            Gerente admin = optionalAdmin.get();
            admin.setNome(adminDetails.getNome());
            admin.setEmail(adminDetails.getEmail());
            admin.setCpf(adminDetails.getCpf());
            admin.setTelefone(adminDetails.getTelefone());
            return gerenteRepository.save(admin);
        }
        return null;
    }
}