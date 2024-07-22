package com.bantads.gerente.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bantads.gerente.model.Gerente;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {
    Optional<Gerente> findByCpf(String cpf);

    @Query("SELECT g FROM Gerente g ORDER BY SIZE(g.contas) ASC")
    Optional<Gerente> findTopByOrderByContasAsc();

    @Query("SELECT g FROM Gerente g ORDER BY SIZE(g.contas) DESC")
    Optional<Gerente> findTopByOrderByContasDesc();
    
}