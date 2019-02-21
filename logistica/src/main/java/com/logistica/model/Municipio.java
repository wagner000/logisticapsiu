package com.logistica.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="municipio")
public class Municipio implements Serializable {

	private static final long serialVersionUID = 1L;

	/*Id 	 INT 		  NOT NULL AUTO_INCREMENT,
	Codigo INT		  NOT NULL,
	Nome 	 VARCHAR(255) NOT NULL,
	Uf	 CHAR(2)	  NOT NULL,*/
	
	private int id;
	private int codigoIbge;
	private String nome;
	private String uf;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(nullable = false, name = "Codigo")
	public int getCodigoIbge() {
		return codigoIbge;
	}
	public void setCodigoIbge(int codigoIbge) {
		this.codigoIbge = codigoIbge;
	}
	
	@NotNull
	@Column(nullable = false, name = "Nome", length = 255)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotNull
	@Column(name = "Uf", nullable = false, length = 2)
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Municipio other = (Municipio) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Municipio [id=" + id + "]";
	}
	
	
	
	
	
}
