package com.logistica.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.logistica.model.Produto;
import com.logistica.util.jpa.Transacional;

public class ProdutoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Produto porId(Long id) {
		return manager.find(Produto.class, id);
	}

	@Transacional
	public Produto salvar(Produto produto) {
		return manager.merge(produto);
	}

	@Transacional
	public void remover(Produto produto) {
		try {

			produto = porId(produto.getId());
			manager.remove(produto);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new Exception("Este Produto não pode ser removido");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<Produto> todos() {

		return manager.createQuery("from Produto", Produto.class).getResultList();
	}

	public List<Produto> porNome(String nome) {

		return this.manager.createQuery("from Produto where upper(nome) like :nome", Produto.class)
				.setParameter("nome", "%"+ nome.toUpperCase() + "%").getResultList();
	}

}
