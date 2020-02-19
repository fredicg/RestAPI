package com.fastfood.foodAPI.infra.repository;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.model.Cozinha;


//@Component 
public class CozinhaRepositoryImpl  {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> listar() {
		
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	public Cozinha Buscar(Long Id) {
		
		return manager.find(Cozinha.class, Id);
	}

	
	@Transactional
	@Override
	public Cozinha Salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	@Transactional
	@Override
	public void Remover(Long Id) {
		Cozinha cozinha = Buscar(Id);
		if (cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cozinha);
		
	}

}
