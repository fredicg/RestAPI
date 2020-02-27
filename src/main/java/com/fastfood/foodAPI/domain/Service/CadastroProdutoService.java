package com.fastfood.foodAPI.domain.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastfood.foodAPI.domain.exception.ProdutoNaoEncontradaException;
import com.fastfood.foodAPI.domain.model.Produto;
import com.fastfood.foodAPI.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	// private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não
	// pode ser removido, pois está em uso.";

	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	public Produto BuscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradaException(restauranteId, produtoId));
	}

}
