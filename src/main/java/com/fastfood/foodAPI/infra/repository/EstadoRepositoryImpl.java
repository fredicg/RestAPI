package com.fastfood.foodAPI.infra.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.model.Estado;
import com.fastfood.foodAPI.domain.repository.EstadoRepository;


//@Component
public class EstadoRepositoryImpl implements EstadoRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Estado> Listar() {
		
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Estado Buscar(Long Id) {
		
		return manager.find(Estado.class, Id);
	}

	@Transactional
	@Override
	public Estado Salvar(Estado estado) {

		return manager.merge(estado);
	}

	@Transactional
	@Override
	public void Remover(Long id) {

		Estado estado = Buscar(id);
		if(estado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(estado);
	}

}
