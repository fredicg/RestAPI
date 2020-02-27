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

import com.fastfood.foodAPI.api.assembler.GrupoInputDisassembler;
import com.fastfood.foodAPI.api.assembler.GrupoModelAssembler;
import com.fastfood.foodAPI.api.model.GrupoDTO;
import com.fastfood.foodAPI.api.model.input.GrupoInput;
import com.fastfood.foodAPI.domain.Service.CadastroGrupoService;
import com.fastfood.foodAPI.domain.exception.EstadoNaoEncontradoException;
import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.model.Grupo;
import com.fastfood.foodAPI.domain.repository.GrupoRepository;

@RestController
@RequestMapping("/Grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@Autowired
	public GrupoInputDisassembler grupoInputDisassembler;

	@GetMapping
	public List<GrupoDTO> Listar() {

		List<Grupo> todosGrupos = grupoRepository.findAll();

		return grupoModelAssembler.toCollectionModel(todosGrupos);

	}

	@GetMapping("/{grupoId}")
	public GrupoDTO Buscar(@PathVariable Long grupoId) {

		Grupo grupo = cadastroGrupo.BuscarOuFalhar(grupoId);

		return grupoModelAssembler.toModel(grupo);

		/*
		 * if (cidade.isPresent()) { return ResponseEntity.ok(cidade.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO Adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		try {
			Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
			grupo = cadastroGrupo.Salvar(grupo);
			return grupoModelAssembler.toModel(grupo);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping("/{grupoId}")
	public GrupoDTO Atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {

		try {
			Grupo grupoAtual = cadastroGrupo.BuscarOuFalhar(grupoId);

			grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

			grupoAtual = cadastroGrupo.Salvar(grupoAtual);

			return grupoModelAssembler.toModel(grupoAtual);
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void Remover(@PathVariable Long grupoId) {

		cadastroGrupo.Remover(grupoId);

		/*
		 * try { cadastroCidade.Remover(estadoId); return
		 * ResponseEntity.noContent().build(); } catch (EntidadeNaoEncontradaException
		 * e) { return ResponseEntity.notFound().build(); }
		 */
	}

}
