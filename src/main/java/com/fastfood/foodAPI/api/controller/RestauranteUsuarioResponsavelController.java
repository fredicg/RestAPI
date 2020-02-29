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

import com.fastfood.foodAPI.api.assembler.UsuarioModelAssembler;
import com.fastfood.foodAPI.api.model.UsuarioDTO;
import com.fastfood.foodAPI.domain.Service.CadastroRestauranteService;
import com.fastfood.foodAPI.domain.model.Restaurante;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired 
	CadastroRestauranteService CadastroRestaurante;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@GetMapping
	public List<UsuarioDTO> listar(@PathVariable Long restauranteId){
		
		Restaurante restaurante = CadastroRestaurante.BuscarOuFalhar(restauranteId);
		
		//na entidade restaurante formaPagamento está mapeado com "set" e "HashSet" então a lista tem que ser "set"
		
		return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis());
		
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		CadastroRestaurante.removerResponsavel(restauranteId, usuarioId);
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		CadastroRestaurante.adicionarResponsavel(restauranteId, usuarioId);
	}

	
	
}
