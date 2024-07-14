import com.ufpr.conta.account.command.models.ModelCommand;;
import com.ufpr.conta.account.query.models.ModelQuery;
import com.ufpr.conta.account.query.repository.AccountQueryRepository;
import com.ufpr.conta.account.command.Repository.AccountRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ContaServices {
    @Autowired
    private AccountCommandRepository commands;

    @Autowired
    private AccountQueryRepository queries;

    @Autowired
    private AccountSender sender;

    public List<AccountDTO> getAllAccounts() {
        List<ModelQuery> accounts = queries.findAll();
        return accounts.stream()
                .map(ModelQuery::toDto)
                .collect(Collectors.toList());
    }

    
}