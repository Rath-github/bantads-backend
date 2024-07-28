package com.ufpr.conta.account.amqp;



import com.ufpr.conta.account.dtos.ContaDto;
import com.ufpr.conta.account.models.ContaModel;
import com.ufpr.conta.account.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ContaHelper {
    final ContaService contaService;

    public ContaHelper(ContaService contaService) {
        this.contaService = contaService;
    }

    public ResponseEntity<String> saveConta(@Valid ContaDto contaDto){
        if(contaService.existsByIdCliente(contaDto.getIdCliente())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Este cliente já possui uma conta!");
        }

        var contaModel = new ContaModel();
        BeanUtils.copyProperties(contaDto, contaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.save(contaModel).getId().toString());
    }

    public ResponseEntity<Object> updateLimite(UUID id, ContaDto contaDto){
        Optional<ContaModel> contaModelOptional = contaService.findByIdCliente(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }

        double limiteAntigo = contaModelOptional.get().getLimite();
        contaModelOptional.get().setLimite(contaDto.getLimite());
        contaService.save(contaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(limiteAntigo);
    }

    public ResponseEntity<String> updateContaByIdGerente(UUID idGerenteAntigo, UUID idGerenteAtual){
        Optional<ContaModel> contaModelOptional = contaService.findByIdGerenteSaga(idGerenteAntigo);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }

        contaModelOptional.get().setIdGerente(idGerenteAtual);
        contaService.save(contaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(contaModelOptional.get().getId().toString());
    }

    public ResponseEntity<String> updateContaByIdGerenteDelete(UUID idGerenteAntigo, UUID idGerenteAtual){
        List<ContaModel> contaModelList = contaService.findByIdGerente(idGerenteAntigo);
        if (contaModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contas não encontradas.");
        }

        for (ContaModel contaModel : contaModelList) {
            contaModel.setIdGerente(idGerenteAtual);
            contaService.save(contaModel);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Contas atualizadas.");
    }

    public ResponseEntity<ContaDto> deleteConta(UUID id){
        Optional<ContaModel> contaModelOptional = contaService.findByIdCliente(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ContaDto());
        }

        ContaDto contaDto = new ContaDto();

        BeanUtils.copyProperties(contaModelOptional.get(), contaDto);

        contaService.delete(contaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(contaDto);
    }
}
