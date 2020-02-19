package com.fastfood.foodAPI.infra.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.model.Cidade;
import com.fastfood.foodAPI.domain.repository.CidadeRepository;

//@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cidade> Listar() {
		
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	public Cidade Buscar(Long Id) {
		
		return manager.find(Cidade.class, Id);
	}

	@Transactional
	@Override
	public Cidade Salvar(Cidade cidade) {
		
		return manager.merge(cidade);
	}

	@Transactional
	@Override
	public void Remover(Long id) {
		Cidade cidade = Buscar(id);
		
		if (cidade == null) {
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(cidade);
		
	}

}
