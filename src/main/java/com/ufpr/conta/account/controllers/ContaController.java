package com.ufpr.conta.account.controllers;

import com.ufpr.conta.account.dtos.ContaDto;
import com.ufpr.conta.account.models.ContaModel;
import com.ufpr.conta.account.services.ContaService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/conta")
public class ContaController {
    final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarConta(@PathVariable(value = "id") UUID id, @RequestBody ContaDto contaDto) {
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
        var contaModel = new ContaModel();
        BeanUtils.copyProperties(contaDto, contaModel);
        contaModel.setId(contaModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(contaService.save(contaModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneConta(@PathVariable(value = "id") UUID id){
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelOptional.get());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<Object> getContaByIdCliente(@PathVariable(value = "idCliente") UUID idCliente){
        Optional<ContaModel> contaModelOptional = contaService.findByIdCliente(idCliente);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelOptional.get());
    }

    @GetMapping("/gerente/{idGerente}")
    public ResponseEntity<Object> getContaByIdGerente(
            @PathVariable(value = "idGerente") UUID idGerente,
            @RequestParam(value = "ativo", required = false) Boolean ativo
    ){
        List<ContaModel> contaModelList;

        if (ativo != null) {
            contaModelList = contaService.findByIdGerenteAndAtivo(idGerente, ativo);
        } else {
            contaModelList = contaService.findByIdGerente(idGerente);
        }

        if (contaModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma conta encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelList);
    }

    @GetMapping("/melhores/{idGerente}")
    public ResponseEntity<Object> getMelhoresContaByIdGerente(
            @PathVariable(value = "idGerente") UUID idGerente
    ){
        List<ContaModel> contaModelList = contaService.findByIdGerenteMelhores(idGerente);

        if (contaModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma conta encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelList);
    }
}
