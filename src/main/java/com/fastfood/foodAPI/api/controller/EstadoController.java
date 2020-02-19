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

	@GetMapping
	public List<Estado> listar() {

		return estadoRepository.findAll();

	}

	@GetMapping("/{estadoId}")
	public Estado Buscar(@PathVariable Long estadoId) {
		return cadastroEstado.BuscarOuFalhar(estadoId);

		/*
		 * if (estado.isPresent()) { return ResponseEntity.ok(estado.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado Salvar(@RequestBody @Valid Estado estado) {
		return cadastroEstado.Salvar(estado);
	}

	@PutMapping("/{estadoId}")
	public Estado Remover(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
		Estado estadoAtual= cadastroEstado.BuscarOuFalhar(estadoId);

		  BeanUtils.copyProperties(estado, estadoAtual, "id"); 
		  
		  return cadastroEstado.Salvar(estadoAtual); 
		 
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public /*ResponseEntity<?> */ void Remover(@PathVariable Long estadoId){
		
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
