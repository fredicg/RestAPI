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

import com.fasterxml.jackson.annotation.JsonView;
import com.fastfood.foodAPI.api.assembler.RestauranteInputDisassembler;
import com.fastfood.foodAPI.api.assembler.RestauranteModelAssembler;
import com.fastfood.foodAPI.api.model.RestauranteDTO;
import com.fastfood.foodAPI.api.model.input.RestauranteInput;
import com.fastfood.foodAPI.api.model.view.RestauranteView;
import com.fastfood.foodAPI.domain.Service.CadastroRestauranteService;
import com.fastfood.foodAPI.domain.exception.EntidadeNaoEncontradaException;
import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.exception.RestauranteNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Restaurante;
import com.fastfood.foodAPI.domain.repository.RestauranteRepository;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	public RestauranteInputDisassembler restauranteInputDisassembler;
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping(params = "projecao=resumo")
	public List<RestauranteDTO> ListarResumido() {

		// Usando DTO
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());

	}
	
	@GetMapping
	public List<RestauranteDTO> Listar() {

		// Usando DTO
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());

	}

	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO Buscar(@PathVariable Long restauranteId) {

		// Usando DTO
		Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);
		return restauranteModelAssembler.toModel(restaurante);

		/*
		 * Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);
		 * RestauranteDTO restauranteDTO = null; return restauranteDTO; /*
		 * if(restaurante != null) { return ResponseEntity.ok(restaurante.get()); }
		 * return ResponseEntity.notFound().build();
		 */

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO Salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			// usando DTO
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			return restauranteModelAssembler.toModel(cadastroRestaurante.Salvar(restaurante));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

		/*
		 * try {
		 * 
		 * restaurante = cadastroRestaurante.Salvar(restaurante); return
		 * ResponseEntity.status(HttpStatus.CREATED).body(restaurante); }catch
		 * (EntidadeNaoEncontradaException e) { return
		 * ResponseEntity.badRequest().body(e.getMessage()); }
		 */
	}

	@PutMapping("/{restauranteId}")
	public RestauranteDTO Atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {

		try {
			
			//usando DTO
			//Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(restauranteId);
			
			//usando modelmapper
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			/*
			BeanUtils.copyProperties(restaurante, restauranteAtual, 
					"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
			*/

			return restauranteModelAssembler.toModel(cadastroRestaurante.Salvar(restauranteAtual));

			/*
			Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(restauranteId);

			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco",
					"dataCadastro");

			return cadastroRestaurante.Salvar(restauranteAtual);
			*/

		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void Remover(@PathVariable Long restauranteId) {
		cadastroRestaurante.Remover(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteId) {
		try {
		cadastroRestaurante.ativar(restauranteId);
		}catch(RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteId) {
		try {
		cadastroRestaurante.inativar(restauranteId);
		}catch(RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId){
		cadastroRestaurante.abrir(restauranteId);
		
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
	}
	
}
