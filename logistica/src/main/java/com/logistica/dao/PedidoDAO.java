package com.logistica.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.logistica.dao.filter.PedidoFilter;
import com.logistica.model.Pedido;
import com.logistica.util.jpa.Transacional;

public class PedidoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Pedido porId(Long id) {
		return manager.find(Pedido.class, id);
	}

	@Transacional
	public Pedido salvar(Pedido pedido) {
		
		try {
			if(pedido.getItens().isEmpty()) {
				throw new Exception("O pedido deve possuir pelo menos um produto.");
			}
			
			return manager.merge(pedido);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Transacional
	public void remover(Pedido pedido) {
		try {

			pedido = porId(pedido.getId());
			manager.remove(pedido);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new Exception("Este Pedido não pode ser removido");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public List<Pedido> todos() {

		return manager.createQuery("from Pedido", Pedido.class).getResultList();
	}
	
	
	/*public List<Pedido> filtrados(PedidoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Pedido.class)
				// fazemos uma associação (join) com cliente e nomeamos como "c"
				.createAlias("solicitante", "s")
				// fazemos uma associação (join) com vendedor e nomeamos como "v"
				.createAlias("responsavel", "r");
		
		if (StringUtils.isNotBlank(filtro.getLiquidacao())) {
			criteria.add(Restrictions.eq("liquidacao", filtro.getLiquidacao()));
		}

		if (StringUtils.isNotBlank(filtro.getNotaFiscal())) {
			criteria.add(Restrictions.eq("notaFiscal", filtro.getNotaFiscal()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			LocalDateTime de = filtro.getDataCriacaoDe().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			de = de.with(LocalTime.MIN);
			System.out.println("**DE: "+de);
			criteria.add(Restrictions.ge("dataPedido", de) );
		}
		
		if (filtro.getDataCriacaoAte() != null) {
			LocalDateTime ate = filtro.getDataCriacaoAte().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			ate = ate.with(LocalTime.MAX);
			System.out.println("**ATE: "+ate);
			criteria.add(Restrictions.le("dataPedido",ate) );
			
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeSolicitante())) {
			// acessamos o nome do cliente associado ao pedido pelo alias "c", criado anteriormente
			criteria.add(Restrictions.ilike("s.nome", filtro.getNomeSolicitante(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeResponsavel())) {
			// acessamos o nome do vendedor associado ao pedido pelo alias "v", criado anteriormente
			criteria.add(Restrictions.ilike("r.nome", filtro.getNomeResponsavel(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatus() != null && filtro.getStatus().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusPedido
			criteria.add(Restrictions.in("status", filtro.getStatus()));
		}
		
		return criteria.addOrder(Order.asc("id")).list();
	}*/
	
	public List<Pedido> filtrados(PedidoFilter filtro){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = builder.createQuery(Pedido.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Pedido> pedidoRoot = criteriaQuery.from(Pedido.class);
		
		if (StringUtils.isNotBlank(filtro.getLiquidacao())) {
			predicates.add(builder.equal(pedidoRoot.get("liquidacao"), filtro.getLiquidacao()));
		}

		if (StringUtils.isNotBlank(filtro.getNotaFiscal())) {
			predicates.add(builder.equal(pedidoRoot.get("notaFiscal"), filtro.getNotaFiscal()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeSolicitante())) {
			predicates.add(builder.like(pedidoRoot.get("solicitante.nome"), filtro.getNomeSolicitante()));
		}
		
		/*if (StringUtils.isNotBlank(filtro.getNomeResponsavel())) {
			// acessamos o nome do vendedor associado ao pedido pelo alias "v", criado anteriormente
			criteria.add(Restrictions.ilike("r.nome", filtro.getNomeResponsavel(), MatchMode.ANYWHERE));
		}
		*/
		
		if (filtro.getDataCriacaoDe() != null) {
			LocalDateTime de = filtro.getDataCriacaoDe().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			de = de.with(LocalTime.MIN);
			System.out.println("**DE: "+de);
			predicates.add( builder.greaterThanOrEqualTo(pedidoRoot.get("dataPedido"), de));
		}
		
		if (filtro.getDataCriacaoAte() != null) {
			LocalDateTime ate = filtro.getDataCriacaoAte().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			ate = ate.with(LocalTime.MAX);
			System.out.println("**ATE: "+ate);
			predicates.add( builder.lessThanOrEqualTo(pedidoRoot.get("dataPedido"), ate));
			
		}
		
		if (filtro.getStatus() != null && filtro.getStatus().length > 0) {
			predicates.add( pedidoRoot.get("status").in(filtro.getStatus()) );
		}
		
		criteriaQuery.select(pedidoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(pedidoRoot.get("id")));
		
		TypedQuery<Pedido> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
