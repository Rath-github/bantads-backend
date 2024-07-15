package com.bantads.saga.models;

import java.io.Serializable;

public class ClienteDTO implements Serializable {
  private Long id;
  private String nome;
  private String cpf;
  private AccountStatus aprovado;
  private Long gerente;
  private Long conta;
  private EnderecoDTO endereco;
  private String email;
  private String password;
  private String role;
  private int salario;

  public ClienteDTO() {}

  public ClienteDTO(Long id, String nome, String cpf, AccountStatus aprovado, Long gerente, Long conta,
                    EnderecoDTO endereco, String email, String password, String role, int salario) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.aprovado = aprovado;
    this.gerente = gerente;
    this.conta = conta;
    this.endereco = endereco;
    this.email = email;
    this.password = password;
    this.role = role;
    this.salario = salario;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public AccountStatus getAprovado() {
    return aprovado;
  }

  public void setAprovado(AccountStatus aprovado) {
    this.aprovado = aprovado;
  }

  public Long getGerente() {
    return gerente;
  }

  public void setGerente(Long gerente) {
    this.gerente = gerente;
  }

  public Long getConta() {
    return conta;
  }

  public void setConta(Long conta) {
    this.conta = conta;
  }

  public EnderecoDTO getEndereco() {
    return endereco;
  }

  public void setEndereco(EnderecoDTO endereco) {
    this.endereco = endereco;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public int getSalario() {
    return salario;
  }

  public void setSalario(int salario) {
    this.salario = salario;
  }
}
