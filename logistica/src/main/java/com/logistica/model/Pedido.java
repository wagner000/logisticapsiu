package com.logistica.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="pedido_puxada")
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Armazem armazem;
	private Usuario solicitante;
	private Usuario responsavel;
	private LocalDateTime dataPedido;
	private LocalDateTime dataAtendimento;
	private String notaFiscal;
	private String liquidacao;
	private StatusPedido status = StatusPedido.EDITANDO;
	private BigDecimal valorFrete = BigDecimal.ZERO;
	private String observacao;
	private List<ItemPedido> itens = new ArrayList<>();
	
	public Pedido() {
		this.dataPedido = LocalDateTime.now();
	}
	
	
	@Transient
	public boolean isNovo() {
		return getId() == null;
	}
	
	@Transient
	public boolean isExistente() {
		return !isNovo();
	}
	
	@Transient
	public boolean isEditando() {
		return StatusPedido.EDITANDO.equals(this.getStatus());
	}
	
	@Transient
	public boolean isAtendido() {
		return StatusPedido.ATENDIDO.equals(this.getStatus());
	}
	
	public void adcionarItemVazio() {
		
		if(this.isEditando()) {
			Produto produto = new Produto();
			ItemPedido item = new ItemPedido();
			item.setProduto(produto);
			item.setPedido(this);
			
			this.getItens().add(0, item); //sempre que chamar esse metodo ele adiciona a primeira linha
		}
	}
	
	public void removerItemVazio() {
		ItemPedido primeiroItem = this.getItens().get(0);
		
		if(primeiroItem != null && primeiroItem.getProduto().getId()==null) {
			this.getItens().remove(0);
		}
	}
	
	@Transient
	public Integer getQuantidadeTotal() {
		Integer total = new Integer(0);
		
		for (ItemPedido item : this.getItens()) {
			if (item.getProduto() != null && item.getProduto().getId() != null) {
				total = total + item.getQuantidade();
			}
		}
		
		return total;
	}
	
	//===================================
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name= "armazem_solicitante", nullable = false, length = 20)
	public Armazem getArmazem() {
		return armazem;
	}
	public void setArmazem(Armazem armazem) {
		this.armazem = armazem;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "solicitante_id", nullable = false)
	public Usuario getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "responsavel_id", nullable = false)
	public Usuario getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}
	
	@NotNull
	@Column(name = "data_pedido", nullable = false)
	public LocalDateTime getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}
	
	@Column(name = "data_atendimento", nullable = false)
	public LocalDateTime getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(LocalDateTime dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	
	@Column(name = "nota_fiscal")
	public String getNotaFiscal() {
		return notaFiscal;
	}
	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	public StatusPedido getStatus() {
		return status;
	}


	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	@Column(name = "valor_frete", precision = 10, scale = 2)
	public BigDecimal getValorFrete() {
		return valorFrete;
	}


	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}


	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Column(columnDefinition = "text")
	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Column(name = "liquidacao", length = 10)
	public String getLiquidacao() {
		return liquidacao;
	}


	public void setLiquidacao(String liquidacao) {
		this.liquidacao = liquidacao;
	}
	
}
