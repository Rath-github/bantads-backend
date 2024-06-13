package com.bantads.gerente.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.gerente.service.GerenteService;
import com.bantads.gerente.model.Gerente;

@RestController
@RequestMapping("/gerentes")
public class GerenteController {
    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    public List<Gerente> listarTodos() {
        return gerenteService.listarTodos();
    }

    @PostMapping
    public Gerente inserirGerente(@RequestBody Gerente gerente) {
        return gerenteService.inserirGerente(gerente);
    }

    @DeleteMapping("/{id}")
    public void removerGerente(@PathVariable Long id) {
        gerenteService.removerGerente(id);
    }

    @PutMapping("/{id}")
    public Gerente alterarGerente(@PathVariable Long id, @RequestBody Gerente gerente) {
        return gerenteService.alterarGerente(id, gerente);
    }
}

