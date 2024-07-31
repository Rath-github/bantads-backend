package com.bantads.gerente.model;

import java.io.Serializable;

public class GerenteDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GerenteDTO() {
		super();
	}

	public GerenteDTO(int id, String nome, String email, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
	}

	private int id;
	private String nome;
	private String email;
	private String cpf;
	private Integer numClientes;

	public Integer getNumClientes() {
		return numClientes;
	}

	public void setNumClientes(Integer numClientes) {
		this.numClientes = numClientes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
