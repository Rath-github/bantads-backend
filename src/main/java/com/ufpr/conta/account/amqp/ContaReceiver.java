package com.ufpr.conta.account.amqp;

import com.ufpr.conta.account.dtos.ContaDto;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Objects;
import java.util.UUID;

@RabbitListener(queues = "conta")
public class ContaReceiver {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private ContaProducer contaProducer;
    @Autowired
    private ContaHelper contaHelper;

    @RabbitHandler
    public ContaTransfer receive(@Payload ContaTransfer contaTransfer) {
        if (contaTransfer.getAction().equals("save-conta")) {
            if (Objects.isNull(contaTransfer.getContaDto().getIdGerente())) {
                contaTransfer.setAction("failed-conta");
                contaTransfer.setMessage(("Nenhum dado de Conta foi passado."));
                return contaTransfer;
            }

            ResponseEntity<String> response = contaHelper.saveConta(contaTransfer.getContaDto());

            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                contaTransfer.setAction("success-conta");
                contaTransfer.setMessage(response.getBody());
                return contaTransfer;
            }

            contaTransfer.setAction("failed-conta");
            contaTransfer.setMessage(Objects.requireNonNull(response.getBody()));
            return contaTransfer;
        }

        if (contaTransfer.getAction().equals("update-limite")) {
            UUID idUpdate = UUID.fromString(contaTransfer.getMessage());

            ResponseEntity<Object> response = contaHelper.updateLimite(idUpdate, contaTransfer.getContaDto());

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                contaTransfer.setAction("success-conta");
                contaTransfer.setMessage(Objects.requireNonNull(response.getBody()).toString());
                return contaTransfer;
            }

            contaTransfer.setAction("failed-conta");
            contaTransfer.setMessage(Objects.requireNonNull(response.getBody()).toString());
            return contaTransfer;
        }

        if (contaTransfer.getAction().equals("update-gerente")) {
            String[] substrings = contaTransfer.getMessage().split("\\+");

            UUID idGerenteAntigo = UUID.fromString(substrings[0]);
            UUID idGerenteAtual = UUID.fromString(substrings[1]);

            ResponseEntity<String> response = contaHelper.updateContaByIdGerente(idGerenteAntigo, idGerenteAtual);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                contaTransfer.setAction("success-conta");
                contaTransfer.setMessage(Objects.requireNonNull(response.getBody()));
                return contaTransfer;
            }

            contaTransfer.setAction("failed-conta");
            contaTransfer.setMessage(Objects.requireNonNull(response.getBody()));
            return contaTransfer;
        }

        if (contaTransfer.getAction().equals("update-gerente-delete")) {
            String[] substrings = contaTransfer.getMessage().split("\\+");

            UUID idGerenteAntigo = UUID.fromString(substrings[0]);
            UUID idGerenteAtual = UUID.fromString(substrings[1]);

            ResponseEntity<String> response = contaHelper.updateContaByIdGerenteDelete(idGerenteAntigo, idGerenteAtual);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                contaTransfer.setAction("success-conta");
                contaTransfer.setMessage(Objects.requireNonNull(response.getBody()));
                return contaTransfer;
            }

            contaTransfer.setAction("failed-conta");
            contaTransfer.setMessage(Objects.requireNonNull(response.getBody()));
            return contaTransfer;
        }

        if (contaTransfer.getAction().equals("delete-conta")) {
            UUID idDelete = UUID.fromString(contaTransfer.getMessage());

            ResponseEntity<ContaDto> response = contaHelper.deleteConta(idDelete);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                contaTransfer.setAction("success-conta");
                contaTransfer.setContaDto(response.getBody());
                contaTransfer.setMessage("Conta deletada com sucesso.");
                return contaTransfer;
            }

            contaTransfer.setAction("failed-conta");
            contaTransfer.setMessage("Conta não encontrada.");
            return contaTransfer;
        }

        contaTransfer.setAction("failed-conta");
        contaTransfer.setMessage("Ação informada não existe.");
        return contaTransfer;
    }
}
