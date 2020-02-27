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

import com.fastfood.foodAPI.api.assembler.UsuarioInputDisassembler;
import com.fastfood.foodAPI.api.assembler.UsuarioModelAssembler;
import com.fastfood.foodAPI.api.model.UsuarioDTO;
import com.fastfood.foodAPI.api.model.input.UsuarioComSenhaInput;
import com.fastfood.foodAPI.api.model.input.UsuarioInput;
import com.fastfood.foodAPI.domain.Service.CadastroUsuarioService;
import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.exception.UsuarioNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Usuario;
import com.fastfood.foodAPI.domain.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	public UsuarioInputDisassembler usuarioInputDisassembler;

	@GetMapping
	public List<UsuarioDTO> Listar() {

		List<Usuario> todosUsuarios = usuarioRepository.findAll();

		return usuarioModelAssembler.toCollectionModel(todosUsuarios);

	}

	@GetMapping("/{usuarioId}")
	public UsuarioDTO Buscar(@PathVariable Long usuarioId) {

		Usuario usuario = cadastroUsuario.BuscarOuFalhar(usuarioId);

		return usuarioModelAssembler.toModel(usuario);

		/*
		 * if (cidade.isPresent()) { return ResponseEntity.ok(cidade.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO Adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		//try {
			
			Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
			usuario = cadastroUsuario.Salvar(usuario);
			return usuarioModelAssembler.toModel(usuario);

		/*} catch (UsuarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}*/

	}

	@PutMapping("/{usuarioId}")
	public UsuarioDTO Atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {

		try {
			Usuario usuarioAtual = cadastroUsuario.BuscarOuFalhar(usuarioId);

			usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

			usuarioAtual = cadastroUsuario.Salvar(usuarioAtual);

			return usuarioModelAssembler.toModel(usuarioAtual);
			
		} catch (UsuarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
	
	/*
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void Remover(@PathVariable Long usuarioId) {

		cadastroUsuario.Remover(usuarioId);

		
		 * try { cadastroCidade.Remover(estadoId); return
		 * ResponseEntity.noContent().build(); } catch (EntidadeNaoEncontradaException
		 * e) { return ResponseEntity.notFound().build(); }
		
	} */

}
