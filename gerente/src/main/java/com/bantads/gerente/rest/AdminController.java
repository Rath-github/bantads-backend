package com.bantads.gerente.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.saga.AdminSaga;

@RestController
@RequestMapping("/gerentes")
public class AdminController {

    @Autowired
    private AdminSaga adminSaga;

    @PostMapping
    public Gerente inserirGerente(@RequestBody Gerente gerente) {
        adminSaga.inserirGerente(gerente);
        return gerente;
    }

    @DeleteMapping("/{id}")
    public void removerGerente(@PathVariable Long id) {
        adminSaga.removerGerente(id);
    }

    @GetMapping("/{id}")
    public Gerente visualizarGerente(@PathVariable Long id) {
        return adminSaga.visualizarGerente(id);
    }

    @PutMapping("/{id}")
    public Gerente editarGerente(@PathVariable Long id, @RequestBody Gerente gerenteDetails) {
        return adminSaga.editarGerente(id, gerenteDetails);
    }
}