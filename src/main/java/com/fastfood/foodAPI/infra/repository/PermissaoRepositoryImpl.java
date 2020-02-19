package com.fastfood.foodAPI.infra.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.model.Permissao;
import com.fastfood.foodAPI.domain.repository.PermissaoRepository;

//@Component
public class PermissaoRepositoryImpl implements PermissaoRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permissao> Listar() {
		
		return manager.createQuery("from permissao", Permissao.class).getResultList();
	}

	@Override
	public Permissao Buscar(Long Id) {
		
		return manager.find(Permissao.class, Id);
	}

	@Transactional
	@Override
	public Permissao Salvar(Permissao permissao) {
		
		return manager.merge(permissao);
	}

	@Override
	public void Remover(Permissao permissao) {
		
		permissao = Buscar(permissao.getId());
		manager.remove(permissao);
		
	}
	
	

}
