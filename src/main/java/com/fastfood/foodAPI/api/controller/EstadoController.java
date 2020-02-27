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

import com.fastfood.foodAPI.api.assembler.EstadoInputDisassembler;
import com.fastfood.foodAPI.api.assembler.EstadoModelAssembler;
import com.fastfood.foodAPI.api.model.EstadoDTO;
import com.fastfood.foodAPI.api.model.input.EstadoInput;
import com.fastfood.foodAPI.domain.Service.CadastroEstadosService;
import com.fastfood.foodAPI.domain.model.Estado;
import com.fastfood.foodAPI.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadosService cadastroEstado;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@GetMapping
	public List<EstadoDTO> listar() {

		List<Estado> todosEstados = estadoRepository.findAll();

		return estadoModelAssembler.toCollectionModel(todosEstados);
	}

	@GetMapping("/{estadoId}")
	public EstadoDTO Buscar(@PathVariable Long estadoId) {

		Estado estado = cadastroEstado.BuscarOuFalhar(estadoId);

		return estadoModelAssembler.toModel(estado);

		/*
		 * if (estado.isPresent()) { return ResponseEntity.ok(estado.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		
		estado = cadastroEstado.Salvar(estado);
		
		return estadoModelAssembler.toModel(estado);
	}

	@PutMapping("/{estadoId}")
	public EstadoDTO Remover(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {

		Estado estadoAtual = cadastroEstado.BuscarOuFalhar(estadoId);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		estadoAtual = cadastroEstado.Salvar(estadoAtual);

		return estadoModelAssembler.toModel(estadoAtual);

		/*
		 * BeanUtils.copyProperties(estado, estadoAtual, "id");
		 * 
		 * return cadastroEstado.Salvar(estadoAtual);
		 */

	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public /* ResponseEntity<?> */ void Remover(@PathVariable Long estadoId) {

		cadastroEstado.Remover(estadoId);

		/*
		 * try {
		 * 
		 * cadastroEstado.Remover(estadoId); return ResponseEntity.noContent().build();
		 * 
		 * } catch (EntidadeNaoEncontradaException e) { return
		 * ResponseEntity.notFound().build(); } catch (EntidadeEmUsoException e) {
		 * return ResponseEntity.status(HttpStatus.CONFLICT) .body(e.getMessage()); }
		 */
	}

}
