package com.fastfood.foodAPI.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fastfood.foodAPI.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

	//@Query("from Restaurante where nome like%:nome% and cozinha.id= :id" )
	//List<Restaurante> ConsultaPorNome(String nome, @Param("id") Long cozinha);
	
	//Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	//List<Restaurante> findTop2ByNomeContaining(String nome);
	
}
