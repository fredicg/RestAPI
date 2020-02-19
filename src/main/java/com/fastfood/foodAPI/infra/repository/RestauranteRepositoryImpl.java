package com.fastfood.foodAPI.infra.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.model.Restaurante;
import com.fastfood.foodAPI.domain.repository.RestauranteRepository;

//@Component
public class RestauranteRepositoryImpl implements RestauranteRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> Listar() {
		
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Restaurante Buscar(Long Id) {

		return manager.find(Restaurante.class, Id);
	}

	@Transactional
	@Override
	public void Remover(Restaurante cozinha) {
		cozinha = Buscar(cozinha.getId());
		manager.remove(cozinha);
	}

	@Transactional
	@Override
	public Restaurante Salvar(Restaurante restaurante) {

		return manager.merge(restaurante);
	}

}
