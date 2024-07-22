package com.bantads.gerente.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bantads.gerente.model.Gerente;
import com.bantads.conta.model.NovaContaRequest;
import com.bantads.gerente.service.GerenteService;
import com.bantads.gerente.producer.GerenteProducer;

import java.util.List;

@RestController
@RequestMapping("/gerentes")
public class GerenteController {
    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private GerenteProducer gerenteProducer;

    @GetMapping
    public List<Gerente> listarTodos() {
        return gerenteService.listarTodos();
    }

    @PostMapping
    public Gerente inserirGerente(@RequestBody Gerente gerente) {
        gerenteProducer.enviarParaInsercaoQueue(gerente);
        return gerente;
    }

    @DeleteMapping("/{id}")
    public void removerGerente(@PathVariable Long id) {
        gerenteProducer.enviarParaRemocaoQueue(id);
    }

    @PutMapping("/{id}")
    public Gerente alterarGerente(@PathVariable Long id, @RequestBody Gerente gerente) {
        return gerenteService.alterarGerente(id, gerente);
    }

    @PostMapping("/autocadastro")
    public void autocadastroGerente(@RequestBody NovaContaRequest novaContaRequest) {
        gerenteProducer.enviarParaAutocadastroQueue(novaContaRequest);
    }
}
