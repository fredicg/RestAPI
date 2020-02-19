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

import com.fastfood.foodAPI.domain.Service.CadastroRestauranteService;
import com.fastfood.foodAPI.domain.exception.EntidadeNaoEncontradaException;
import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.model.Restaurante;
import com.fastfood.foodAPI.domain.repository.RestauranteRepository;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public List<Restaurante> Listar(){
		
		return restauranteRepository.findAll();
	}
	

	@GetMapping("/{restauranteId}")
	public Restaurante Buscar(@PathVariable Long restauranteId){
		return cadastroRestaurante.BuscarOuFalhar(restauranteId);
		
		/*
		 * if(restaurante != null) { return ResponseEntity.ok(restaurante.get()); }
		 * return ResponseEntity.notFound().build();
		 */
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante Salvar(@RequestBody @Valid Restaurante restaurante) {
		
		try {
		return cadastroRestaurante.Salvar(restaurante);
		}
		catch (EntidadeNaoEncontradaException e) {
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
	public Restaurante Atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante ){
		

			Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(restauranteId);
		
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");
			
			try {
			return cadastroRestaurante.Salvar(restauranteAtual);
			}
			catch (EntidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage());
			}
			
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void Remover(@PathVariable Long restauranteId) {
		cadastroRestaurante.Remover(restauranteId);
	}
}
