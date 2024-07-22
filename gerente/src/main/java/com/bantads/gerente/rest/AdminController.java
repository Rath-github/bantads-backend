package com.bantads.gerente.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.saga.AdminSaga;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminSaga adminSaga;

    @PostMapping
    public Gerente inserirAdmin(@RequestBody Gerente gerente) {
        adminSaga.inserirAdmin(gerente);
        return gerente;
    }

    @DeleteMapping("/{id}")
    public void removerAdmin(@PathVariable Long id) {
        adminSaga.removerAdmin(id);
    }

    @GetMapping("/{id}")
    public Optional<Gerente> visualizarAdmin(@PathVariable Long id) {
        return adminSaga.visualizarAdmin(id);
    }

    @PutMapping("/{id}")
    public Gerente editarAdmin(@PathVariable Long id, @RequestBody Gerente adminDetails) {
        return adminSaga.editarAdmin(id, adminDetails);
    }
}