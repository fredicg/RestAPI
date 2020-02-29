package com.fastfood.foodAPI.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastfood.foodAPI.api.assembler.PermissaoInputDisassembler;
import com.fastfood.foodAPI.api.assembler.PermissaoModelAssembler;
import com.fastfood.foodAPI.api.model.PermissaoDTO;
import com.fastfood.foodAPI.domain.Service.CadastroGrupoService;
import com.fastfood.foodAPI.domain.Service.CadastroPermissaoService;
import com.fastfood.foodAPI.domain.model.Grupo;
import com.fastfood.foodAPI.domain.model.Permissao;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private CadastroPermissaoService cadastroPermissao;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Autowired
	public PermissaoInputDisassembler permissaoInputDisassembler;

	@Autowired
	public CadastroGrupoService cadastroGrupo;

	@GetMapping
	public List<PermissaoDTO> Listar(@PathVariable Long grupoId)  {

		Grupo grupo = cadastroGrupo.BuscarOuFalhar(grupoId);

		return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
	}

	@GetMapping("/{permissaoId}")
	public PermissaoDTO Buscar(@PathVariable Long permissaoId) {

		Permissao permissao = cadastroPermissao.BuscarOuFalhar(permissaoId);

		return permissaoModelAssembler.toModel(permissao);

		/*
		 * if (cidade.isPresent()) { return ResponseEntity.ok(cidade.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	/*
	 * @PostMapping
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public PermissaoDTO
	 * Adicionar(@RequestBody @Valid PermissaoInput permissaoInput) { try {
	 * Permissao permissao =
	 * permissaoInputDisassembler.toDomainObject(permissaoInput); permissao =
	 * cadastroPermissao.Salvar(permissao); return
	 * PermissaoModelAssembler.toModel(permissao);
	 * 
	 * } catch (PermissaoNaoEncontradoException e) { throw new
	 * NegocioException(e.getMessage(), e); }
	 * 
	 * }
	 */

	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
	}

	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
	}

}
