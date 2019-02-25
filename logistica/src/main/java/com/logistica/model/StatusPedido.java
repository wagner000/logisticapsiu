package com.logistica.model;

public enum StatusPedido {

	EDITANDO("Editando"),
	ATENDIDO("Atendido"),
	CANCELADO("Cancelado"),
	EMITIDO("Emitido");
	
	private String descricao;
	
	private StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
