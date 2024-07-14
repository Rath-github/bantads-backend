package com.ufpr.conta.account.amqp.saga;
import com.ufpr.conta.account.services.ContaServices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AccountSagaListener {

    @Autowired
    private ContaServices ContaServices;

    @Autowired
    private AccountSender accountSender;

    @RabbitListener(queues = "account-saga")
    public AccountTransfer receive(@Payload AccountTransfer transfer) {
        System.out.println("[ACCOUNT-SAGA] Action received: " + transfer.getAction());

        switch (transfer.getAction()) {
            case "create-account":
                try {
                    AccountDTO createdAccount = accountServices.createAccount(transfer.getAccount());
                    transfer.setAccount(createdAccount);
                    transfer.setAction("create-account-ok");
                } catch (Exception e) {
                    transfer.setAction("create-account-error");
                    transfer.setAccount(null);
                }
                break;
            case "delete-account":
                try {
                    accountServices.deleteAccount(transfer.getAccount().getId());
                    transfer.setAccount(null);
                    transfer.setAction("delete-account-ok");
                } catch (Exception e) {
                    transfer.setAction("delete-account-error");
                }
                break;
            // Adicione outros casos de ação conforme necessário
            default:
                System.out.println("Unsupported action: " + transfer.getAction());
                break;
        }

        // Exemplo de envio de resposta após processamento
        accountSender.send(transfer.getAccount(), transfer.getAction());

        return transfer;
    }
}