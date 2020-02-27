package com.fastfood.foodAPI.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastfood.foodAPI.api.assembler.CidadeInputDisassembler;
import com.fastfood.foodAPI.api.assembler.CidadeModelAssembler;
import com.fastfood.foodAPI.api.model.CidadeDTO;
import com.fastfood.foodAPI.api.model.input.CidadeInput;
import com.fastfood.foodAPI.domain.Service.CadastroCidadeService;
import com.fastfood.foodAPI.domain.exception.EstadoNaoEncontradoException;
import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.model.Cidade;
import com.fastfood.foodAPI.domain.repository.CidadeRepository;

@RestController
@RequestMapping("/Cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	public CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public List<CidadeDTO> Listar() {

		List<Cidade> todasCidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(todasCidades);

	}

	@GetMapping("/{cidadeId}")
	public CidadeDTO Buscar(@PathVariable Long cidadeId) {

		Cidade cidade = cadastroCidade.BuscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);

		/*
		 * if (cidade.isPresent()) { return ResponseEntity.ok(cidade.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO Adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			cidade = cadastroCidade.Salvar(cidade);
			return cidadeModelAssembler.toModel(cidade);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping("/{cidadeId}")
	public CidadeDTO Atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidadeAtual = cadastroCidade.BuscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cadastroCidade.Salvar(cidadeAtual);

			return cidadeModelAssembler.toModel(cidadeAtual);
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void Remover(@PathVariable Long estadoId) {

		cadastroCidade.Remover(estadoId);

		/*
		 * try { cadastroCidade.Remover(estadoId); return
		 * ResponseEntity.noContent().build(); } catch (EntidadeNaoEncontradaException
		 * e) { return ResponseEntity.notFound().build(); }
		 */
	}

}
