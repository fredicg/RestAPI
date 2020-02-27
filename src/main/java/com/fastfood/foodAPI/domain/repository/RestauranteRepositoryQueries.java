package com.fastfood.foodAPI.domain.repository;
import java.math.BigDecimal;
import java.util.List;

import com.fastfood.foodAPI.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}