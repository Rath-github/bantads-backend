package com.bantads.gerente.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gerente")
public class Gerente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "email")
	private String email;
	@Column(name = "cpf")
	private String cpf;
	@Column(name = "numclientes")
	private int numClientes = 0;
	@Column(name = "password")
	private String password;

	public Gerente() {
		super();
	}

	public Gerente(int id, String nome, String email, String password, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.cpf = cpf;
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

	public static Long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getNumClientes() {
		return numClientes;
	}

	public void setNumClientes(int numClientes) {
		this.numClientes = numClientes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
