package com.fastfood.foodAPI.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastfood.foodAPI.domain.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{

	//@Query("from Restaurante where nome like%:nome% and cozinha.id= :id" )
	//List<Restaurante> ConsultaPorNome(String nome, @Param("id") Long cozinha);
	
	//Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	//List<Restaurante> findTop2ByNomeContaining(String nome);
	
}
