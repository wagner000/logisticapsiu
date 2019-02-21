package com.logistica.dao.filter;

import java.io.Serializable;
import java.util.Date;

import com.logistica.model.Armazem;
import com.logistica.model.StatusPedido;

/**
 * @author Wagner
 *
 */
public class PedidoFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Armazem[] armazem;
	private String nomeSolicitante;
	private String nomeResponsavel;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String notaFiscal;
	private String liquidacao;
	private StatusPedido[] status;
	
	
	//=====================================================================
	
	public Armazem[] getArmazem() {
		return armazem;
	}
	public void setArmazem(Armazem[] armazem) {
		this.armazem = armazem;
	}
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}
	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public Date getDataCriacaoDe() {
		return dataCriacaoDe;
	}
	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}
	public Date getDataCriacaoAte() {
		return dataCriacaoAte;
	}
	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}
	public String getNotaFiscal() {
		return notaFiscal;
	}
	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	public String getLiquidacao() {
		return liquidacao;
	}
	public void setLiquidacao(String liquidacao) {
		this.liquidacao = liquidacao;
	}
	public StatusPedido[] getStatus() {
		return status;
	}
	public void setStatus(StatusPedido[] status) {
		this.status = status;
	}
	
}