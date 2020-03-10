package com.fastfood.foodAPI.infra.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fastfood.foodAPI.domain.Service.VendaQueryService;
import com.fastfood.foodAPI.domain.filter.VendaDiariaFilter;
import com.fastfood.foodAPI.domain.model.Pedido;
import com.fastfood.foodAPI.domain.model.StatusPedido;
import com.fastfood.foodAPI.domain.model.dto.VendaDiaria;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
	
	@Autowired
	private EntityManager manager;

	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		var predicates = new ArrayList<Predicate>();
		
		//para prevenir erro de data UTC
		var functionConvertTzDataCriacao = builder.function("convert_tz", Date.class,
				root.get("data_criacao"), builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functionDateDataCriacao = builder.function(
				"date", Date.class, functionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class, 
				functionDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valor_total")));
		
		
		
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
		
		if (filtro.getDataCriacaoInicio() != null) {							  
			predicates.add(builder.greaterThanOrEqualTo(root.get("data_criacao"), filtro.getDataCriacaoInicio()));
		}
		
		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("data_criacao"), filtro.getDataCriacaoFim()));
		}
		
		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		
		return manager.createQuery(query).getResultList();
	}

}
