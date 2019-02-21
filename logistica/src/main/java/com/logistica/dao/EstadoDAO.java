package com.logistica.dao;



import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.logistica.model.Estado;


public class EstadoDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Estado porId(int id) {
		return manager.find(Estado.class, id);
	}
	
	
	public List<Estado> todos(){
		
		return manager.createQuery("from Estado",Estado.class).getResultList();
		
		
		
	}
}
