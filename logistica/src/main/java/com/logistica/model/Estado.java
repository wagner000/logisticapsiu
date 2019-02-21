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
@Table(name="estado")
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;

	/*Id       INT          NOT NULL AUTO_INCREMENT,
    CodigoUf INT          NOT NULL,
    Nome     VARCHAR (50) NOT NULL,
    Uf       CHAR 	 (2)  NOT NULL,
    Regiao   INT	      NOT NULL,*/
	
	private int id;
	private int codigoUf;
	private String nome;
	private String uf;
	private int regiao;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@NotNull
	@Column(nullable = false, name = "CodigoUf")
	public int getCodigoUf() {
		return codigoUf;
	}
	public void setCodigoUf(int codigoUf) {
		this.codigoUf = codigoUf;
	}
	
	@NotNull
	@Column(nullable = false, length = 50, name = "Nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotNull
	@Column(nullable = false, length = 2, name = "Uf")
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	@Column(nullable = false, name = "Regiao")
	public int getRegiao() {
		return regiao;
	}
	public void setRegiao(int regiao) {
		this.regiao = regiao;
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
		Estado other = (Estado) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
