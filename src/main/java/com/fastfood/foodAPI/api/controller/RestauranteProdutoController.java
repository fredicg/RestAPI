package com.fastfood.foodAPI.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastfood.foodAPI.api.assembler.ProdutoInputDisassembler;
import com.fastfood.foodAPI.api.assembler.ProdutoModelAssembler;
import com.fastfood.foodAPI.api.model.ProdutoDTO;
import com.fastfood.foodAPI.api.model.input.ProdutoInput;
import com.fastfood.foodAPI.domain.Service.CadastroProdutoService;
import com.fastfood.foodAPI.domain.Service.CadastroRestauranteService;
import com.fastfood.foodAPI.domain.model.Produto;
import com.fastfood.foodAPI.domain.model.Restaurante;
import com.fastfood.foodAPI.domain.repository.ProdutoRepository;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository ;

	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante; 

	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;

	@Autowired
	public ProdutoInputDisassembler produtoInputDisassembler;

	@GetMapping
	public List<ProdutoDTO> Listar(@PathVariable Long restauranteId) {

		Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);
		
		List<Produto> produtos = produtoRepository.findByRestaurante(restaurante);

		return produtoModelAssembler.toCollectionModel(produtos);

	}

	@GetMapping("/{produtoId}")
	public ProdutoDTO Buscar(@PathVariable Long restauranteId ,@PathVariable Long produtoId) {

		Produto produto = cadastroProduto.BuscarOuFalhar(restauranteId, produtoId);

		return produtoModelAssembler.toModel(produto);

		/*
		 * if (cidade.isPresent()) { return ResponseEntity.ok(cidade.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO Adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		
		Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);
		
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		
		produto = cadastroProduto.salvar(produto);

		return produtoModelAssembler.toModel(produto);

	}

	@PutMapping("/{produtoId}")
	public ProdutoDTO Atualizar(@PathVariable Long restauranteId ,@PathVariable Long produtoId, 
			@RequestBody @Valid ProdutoInput produtoInput) {
		
			Produto produtoAtual = cadastroProduto.BuscarOuFalhar(restauranteId, produtoId);
			
			produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

			produtoAtual = cadastroProduto.salvar(produtoAtual);

			return produtoModelAssembler.toModel(produtoAtual);
	}

	/*@DeleteMapping("/{formaPagamentoI}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void Remover(@PathVariable Long formaPagamentoId) {

		cadastroFormaPagamento.Remover(formaPagamentoId);

		/*
		 * try { cadastroCidade.Remover(estadoId); return
		 * ResponseEntity.noContent().build(); } catch (EntidadeNaoEncontradaException
		 * e) { return ResponseEntity.notFound().build(); }
		
	}*/

}
