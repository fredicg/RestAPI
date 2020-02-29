package com.fastfood.foodAPI.domain.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastfood.foodAPI.domain.exception.GrupoNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Permissao;
import com.fastfood.foodAPI.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao BuscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(permissaoId));
	}
}
