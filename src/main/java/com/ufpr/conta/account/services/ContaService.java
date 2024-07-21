import com.ufpr.conta.account.command.Repository.AccountCommandRepository;
import com.ufpr.conta.account.query.Repository.AccountQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService {

    @Autowired
    private AccountCommandRepository AccountCommandRepository;

    @Autowired
    private AccountQueryRepository AccountQueryRepository;

    @Autowired
    private AccountSender sender;

    @Transactional
    public ContaModel save(ContaModel contaModel) {
        return AccountCommandRepository.save(contaModel);
    }

    public Optional<ContaModel> findById(UUID id) {
        return AccountQueryRepository.findById(id);
    }

    public Optional<ContaModel> findByIdCliente(UUID idCliente) {
        return AccountQueryRepository.findByIdCliente(idCliente);
    }

    public List<ContaModel> findByIdGerente(UUID idGerente) {
        return AccountQueryRepository.findByIdGerente(idGerente);
    }

    public List<ContaModel> findByIdGerenteAndAtivo(UUID idGerente, Boolean ativo) {
        return AccountQueryRepository.findByIdGerenteAndAtivo(idGerente, ativo);
    }

    public Optional<ContaModel> findByIdGerenteSaga(UUID idGerenteAntigo) {
        return AccountQueryRepository.findByIdGerenteSaga(idGerenteAntigo);
    }

    @Transactional
    public void delete(ContaModel contaModel) {
        AccountCommandRepository.delete(contaModel);
    }

    public boolean existsByIdCliente(UUID idCliente) {
        return AccountQueryRepository.existsByIdCliente(idCliente);
    }

 

    public List<AccountDTO> getAllAccounts() {
        List<ModelQuery> accounts = queryRepository.findAll();
        return accounts.stream()
                .map(ModelQuery::toDto)
                .collect(Collectors.toList());
    }
}