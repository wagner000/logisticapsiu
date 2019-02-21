package com.logistica.model;

public enum Armazem {

	SAOLUIS(1,"São Luís"),
	IMPERATRIZ(2, "Imperatriz"),
	SANTAINES(3,"Santa Inês"),
	CHAPADINHA(4,"Chapadinha"),
	PRESIDENTEDUTRA(5,"Presidente Dutra");
	
	private String nome;
	private int codigo;
	
	private Armazem(int cod, String nome) {
		this.nome = nome;
		this.codigo = cod;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
}
