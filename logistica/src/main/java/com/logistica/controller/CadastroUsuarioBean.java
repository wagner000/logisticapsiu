package com.logistica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.logistica.dao.UsuarioDAO;
import com.logistica.model.Usuario;
import com.logistica.util.jsf.FacesUtil;
import com.logistica.util.jsf.NegocioException;

/**
 * @author Wagner
 *
 */
@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	private Usuario usuarioSelecionado;
	private List<Usuario> usuarios;
	private String filtroNome;
	
	public CadastroUsuarioBean() {
		filtroNome = new String();
		usuarioSelecionado = new Usuario();
	}

	public void init() {
		if(FacesUtil.isNotPostback()) {
			usuarios = usuarioDAO.todos();
		}
	}
	
	public void limpar() {
		usuarioSelecionado = new Usuario();
		usuarios = usuarioDAO.todos();
	}
	
	public void novo() {
		usuarioSelecionado = new Usuario();
	}
	
	public void salvar() {
		try {
			this.usuarioSelecionado = usuarioDAO.salvar(this.usuarioSelecionado);
			limpar();
			
			FacesUtil.addInfoMessage("Usuario salvo com sucesso!");
		} catch (Exception ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void excluir() {
		usuarioDAO.remover(usuarioSelecionado);
		usuarios.remove(usuarioSelecionado);
		FacesUtil.addInfoMessage("Usuário "+usuarioSelecionado.getNome()+" removido com sucesso.");
	}

	
	
	
	//===================================================================================
	
	
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}
	
	
}
