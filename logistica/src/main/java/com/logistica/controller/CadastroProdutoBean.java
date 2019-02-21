package com.logistica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.logistica.dao.ProdutoDAO;
import com.logistica.model.Produto;

/**
 * @author Wagner
 *
 */
@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDAO produtoDAO;
	private List<Produto> produtos;
	private String embalagem;
	
	public void init() {
		produtos = produtoDAO.todos();
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public String getEmbalagem() {
		return embalagem;
	}

	public void setEmbalagem(String embalagem) {
		this.embalagem = embalagem;
	}
	
	
}
