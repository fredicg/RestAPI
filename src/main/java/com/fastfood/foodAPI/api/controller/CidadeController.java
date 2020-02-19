package com.fastfood.foodAPI.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

	@GetMapping
	public List<Cidade> Listar() {
		
		return cidadeRepository.findAll();
	}

	@GetMapping("/{cidadeId}")
	public Cidade Buscar(@PathVariable Long cidadeId) {

		
		return cadastroCidade.BuscarOuFalhar(cidadeId);
		

		/*
		 * if (cidade.isPresent()) { return ResponseEntity.ok(cidade.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade Adicionar(@RequestBody @Valid Cidade cidade) {
		try {
		return cadastroCidade.Salvar(cidade);
		}
		catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}

	@PutMapping("/{cidadeId}")
	public Cidade Atualizar(@PathVariable Long cidadeId, @RequestBody @Valid Cidade cidade) {

		Cidade cidadeAtual = cadastroCidade.BuscarOuFalhar(cidadeId);

		// terceiro paramentro são propriedade que deseja ignorar
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		try {
			return cadastroCidade.Salvar(cidadeAtual);
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
