package com.logistica.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.logistica.model.Usuario;
import com.logistica.util.jpa.Transacional;

public class UsuarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	@Transacional
	public Usuario salvar(Usuario usuario) {
		return manager.merge(usuario);
	}

	@Transacional
	public void remover(Usuario usuario) {
		try {

			// TODO verificar se existe cadastro com este usuario

			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new Exception("Este Usuario não pode ser removido");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public List<Usuario> todos() {

		return manager.createQuery("from Usuario", Usuario.class).getResultList();
	}
	
	public Usuario porEmail(String email) {
		Usuario usuario = null;
		
		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
				.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}
		
		return usuario;
}

}
