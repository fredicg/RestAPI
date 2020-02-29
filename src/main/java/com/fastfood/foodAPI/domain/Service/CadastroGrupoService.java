package com.fastfood.foodAPI.domain.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.exception.CidadeNaoEncontradaException;
import com.fastfood.foodAPI.domain.exception.EntidadeEmUsoException;
import com.fastfood.foodAPI.domain.exception.GrupoNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Grupo;
import com.fastfood.foodAPI.domain.model.Permissao;
import com.fastfood.foodAPI.domain.repository.GrupoRepository;


@Service
public class CadastroGrupoService {

	private static final String MSG_GRUPO_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissao;

	@Transactional
	public Grupo Salvar(Grupo grupo) {
		
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void Remover(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
			
		}catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(grupoId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = BuscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.BuscarOuFalhar(permissaoId);
		
		grupo.removerPermissao(permissao);
	}
	
	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = BuscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.BuscarOuFalhar(permissaoId);
		
		grupo.adicionarPermissao(permissao);
	}
	
	
	public Grupo BuscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
				
	}
}
