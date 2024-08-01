package com.bantads.gerente.rest;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.saga.AdminSaga;

@RestController
@RequestMapping("/administrador")
public class AdminController {

    @Autowired
    private AdminSaga adminSaga;

    @PostMapping("/salvar")
    public Gerente inserirGerente(@RequestBody Gerente gerente) {
        adminSaga.inserirGerente(gerente);
        return gerente;
    }

    @PutMapping("/atualizar/{id}")
    public Gerente editarGerente(@PathVariable Long id, @RequestBody Gerente gerenteDetails) {
        return adminSaga.editarGerente(id, gerenteDetails);
    }

    @DeleteMapping("/remover/{id}")
    public void removerGerente(@PathVariable Long id) {
        adminSaga.removerGerente(id);
    }

    @GetMapping("/gerentes/{id}")
    public Gerente visualizarGerente(@PathVariable Long id) {
        return adminSaga.visualizarGerente(id);
    }

    @GetMapping("/gerentes")
    public List<Gerente> listarGerentes() {
        return adminSaga.listarGerentes();
    }

    @GetMapping("/clientes/{id}")
    public Cliente visualizarCliente(@PathVariable Long id) {
        return adminSaga.visualizarCliente(id);
    }

    @GetMapping("/clientes")
    public List<Cliente> listarClientes() {
        return adminSaga.listarClientes();
    }
}