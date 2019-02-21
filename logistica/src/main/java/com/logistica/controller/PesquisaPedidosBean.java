package com.logistica.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.logistica.dao.PedidoDAO;
import com.logistica.dao.filter.PedidoFilter;
import com.logistica.model.Pedido;
import com.logistica.model.StatusPedido;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoDAO pedidoDao;
	
	private PedidoFilter filtro = new PedidoFilter();
	private List<Pedido> pedidosFiltrados;
	
	public PesquisaPedidosBean() {
		filtro = new PedidoFilter();
		pedidosFiltrados = new ArrayList<>();
	}

	public void pesquisar() {
		pedidosFiltrados = pedidoDao.filtrados(filtro);
		//pedidosFiltrados = pedidoDao.todos();
	}
	
	public StatusPedido[] getStatuses() {
		return StatusPedido.values();
	}
	
	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}
	
}