package com.bantads.gerente.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bantads.gerente.model.Gerente;

public interface GerenteRepository extends JpaRepository<Gerente, Integer> {
	public Gerente findByCpf(String cpf);

	@Query("from Gerente where email = :email and cpf = :cpf")
	public Gerente findByEmailAndCpf(@Param("email") String email,
			@Param("cpf") String cpf);

	public Gerente findFirstByOrderByNumClientes();

	public Optional<Gerente> findById(int id);

	public Gerente findByEmail(String email);

}
