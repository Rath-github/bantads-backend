package com.tads.dac.cliente.repository;

import com.tads.dac.cliente.model.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    /**
     * Atualiza o gerente de todos os clientes que possuem o ID de gerente antigo para o novo ID de gerente e nome de gerente.
     * @param idNew O novo ID de gerente.
     * @param nomeNew O novo nome de gerente.
     * @param idOld O antigo ID de gerente.
     * @return O número de linhas alteradas no banco de dados.
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update cliente set id_gerente = ?1 , nome_gerente = ?2 where id_gerente = ?3 ;")
    int transferirTodosClientes(Long idNew, String nomeNew, Long idOld);
    
    /**
     * Atualiza o gerente de um cliente específico pelo ID do cliente.
     * @param idGerente O novo ID de gerente.
     * @param nomeGerente O novo nome de gerente.
     * @param idCliente O ID do cliente a ser atualizado.
     * @return O número de linhas alteradas no banco de dados.
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update cliente set id_gerente = ?1 , nome_gerente = ?2 where id = ?3")
    int updateGerenteCliente(Long idGerente, String nomeGerente, Long idCliente);
    
    /**
     * Busca um cliente pelo seu endereço de e-mail.
     * @param email O endereço de e-mail do cliente a ser buscado.
     * @return Um objeto Optional contendo o cliente, se encontrado.
     */
    public Optional<Cliente> findByEmail(String email);
}
