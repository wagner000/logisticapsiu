package com.logistica.dao;



import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.logistica.model.Municipio;


public class MunicipioDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Municipio porId(int id) {
		return this.manager.find(Municipio.class, id);
	}
	
	public List<Municipio> todos(){
		
		return manager.createQuery("from Municipio",Municipio.class).getResultList();
	}
	
	public List<Municipio> porEstado(String uf){
	return manager.createQuery("from Municipio where uf = :uf", Municipio.class)
			.setParameter("uf", uf).getResultList();
	}
}

	
	
