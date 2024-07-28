package com.ufpr.conta.account.amqp;
import com.ufpr.conta.account.dtos.ContaDto;
import java.io.Serializable;

public class ContaTransfer implements Serializable {
    ContaDto contaDto;
    String action;
    String message;

    public ContaTransfer() {
    }

    public ContaTransfer(ContaDto contaDto, String action) {
        this.contaDto = contaDto;
        this.action = action;
    }

    public ContaDto getContaDto() {
        return this.contaDto;
    }

    public void setContaDto(ContaDto contaDto) {
        this.contaDto = contaDto;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}