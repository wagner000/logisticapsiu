package com.logistica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.logistica.dao.PedidoDAO;
import com.logistica.dao.ProdutoDAO;
import com.logistica.dao.UsuarioDAO;
import com.logistica.model.Armazem;
import com.logistica.model.ItemPedido;
import com.logistica.model.Pedido;
import com.logistica.model.Produto;
import com.logistica.model.Usuario;
import com.logistica.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido;
	private List<Usuario> usuarios;

	@Inject
	private PedidoDAO pedidoDAO;
	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private ProdutoDAO produtoDAO;
	
	private Produto produtoLinhaEditavel;
	private String codigo;
	
	public CadastroPedidoBean() {
		//limpar();
	}
	
	
	public void init() {
		if(FacesUtil.isNotPostback()) {
			if(pedido == null) {
				limpar();
			}
			usuarios = usuarioDAO.todos();
			this.pedido.adcionarItemVazio();
		}
	}
	
	public List<Produto> completarProduto(String nome){
		return produtoDAO.porNome(nome);
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);
		
		if(produtoLinhaEditavel != null) {
			if(this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Produto já consta no pedido!");
			}else {
				item.setProduto(this.produtoLinhaEditavel);
				this.pedido.adcionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.codigo = null;
			}
		}
	}
	
	//verifica se o produto já está inserido no pedido
	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for(ItemPedido item : this.pedido.getItens()) {
			if(produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		return existeItem;
	}


	public void carregarProdutoPorCodigo() {
		if(StringUtils.isNotEmpty(this.codigo)) {
			this.produtoLinhaEditavel = this.produtoDAO.porId(new Long(this.codigo));
			this.carregarProdutoLinhaEditavel();
		}
	}
	
	public void atualizarQuantidade(ItemPedido item, int linha) {
		if(item.getQuantidade()<1) {
			if(linha ==0) {
				item.setQuantidade(1);
			}else {
				this.getPedido().getItens().remove(linha);
			}
		}
	}
	
	public void limpar() {
		pedido = new Pedido();
	}
	
	public boolean isEditando(){
		return this.pedido.getId() != null;
	}
	
	public void salvar() {
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.pedidoDAO.salvar(this.pedido);
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} finally {
			this.pedido.adcionarItemVazio();
		}
	}
	
	
	
	public void emitir() {
		this.pedido.removerItemVazio();
		try {
			this.pedido = pedidoDAO.emitir(pedido);
			FacesUtil.addInfoMessage("Pedido Emitido com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar() {
		this.pedido.removerItemVazio();
		this.pedido = pedidoDAO.cancelar(pedido);
		FacesUtil.addInfoMessage("Pedido Cancelado com sucesso.");
	}
	
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Armazem[] getArmazem() {
		return Armazem.values();
	}


	public List<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}


	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}


	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
