package com.ufpr.conta.account.amqp;

import com.ufpr.conta.account.dto.ContaDto;
import com.ufpr.conta.account.command.models.ModelCommand;
import com.ufpr.conta.account.query.models.ModelQuery;
import com.ufpr.conta.account.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ContaHandler {
    final ContaService contaService;

    public ContaHandler(ContaService contaService) {
        this.contaService = contaService;
    }

    public ResponseEntity<String> saveConta(@Valid ContaDto contaDto) {
        if (contaService.existsByIdCliente(contaDto.getIdCliente())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Este cliente já possui uma conta!");
        }

        var modelCommand = new ModelCommand();
        BeanUtils.copyProperties(contaDto, modelCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.save(modelCommand).getId().toString());
    }

    public ResponseEntity<Object> updateLimite(UUID id, ContaDto contaDto) {
        Optional<ModelQuery> modelQueryOptional = contaService.findByIdCliente(id);
        if (!modelQueryOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }

        double limiteAntigo = modelQueryOptional.get().getLimite();
        ModelCommand modelCommand = new ModelCommand();
        BeanUtils.copyProperties(modelQueryOptional.get(), modelCommand);
        modelCommand.setLimite(contaDto.getLimite());
        contaService.save(modelCommand);
        return ResponseEntity.status(HttpStatus.OK).body(limiteAntigo);
    }

    public ResponseEntity<String> updateContaByIdGerente(UUID idGerenteAntigo, UUID idGerenteAtual) {
        Optional<ModelQuery> modelQueryOptional = contaService.findByIdGerenteSaga(idGerenteAntigo);
        if (!modelQueryOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }

        ModelCommand modelCommand = new ModelCommand();
        BeanUtils.copyProperties(modelQueryOptional.get(), modelCommand);
        modelCommand.setIdGerente(idGerenteAtual);
        contaService.save(modelCommand);
        return ResponseEntity.status(HttpStatus.OK).body(modelCommand.getId().toString());
    }

    public ResponseEntity<String> updateContaByIdGerenteDelete(UUID idGerenteAntigo, UUID idGerenteAtual) {
        List<ModelQuery> modelQueryList = contaService.findByIdGerente(idGerenteAntigo);
        if (modelQueryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contas não encontradas.");
        }

        for (ModelQuery modelQuery : modelQueryList) {
            ModelCommand modelCommand = new ModelCommand();
            BeanUtils.copyProperties(modelQuery, modelCommand);
            modelCommand.setIdGerente(idGerenteAtual);
            contaService.save(modelCommand);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Contas atualizadas.");
    }

    public ResponseEntity<ContaDto> deleteConta(UUID id) {
        Optional<ModelQuery> modelQueryOptional = contaService.findByIdCliente(id);
        if (!modelQueryOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ContaDto());
        }

        ContaDto contaDto = new ContaDto();
        ModelCommand modelCommand = new ModelCommand();

        BeanUtils.copyProperties(modelQueryOptional.get(), contaDto);
        BeanUtils.copyProperties(modelQueryOptional.get(), modelCommand);

        contaService.delete(modelCommand);
        return ResponseEntity.status(HttpStatus.OK).body(contaDto);
    }
}
