package com.fastfood.foodAPI.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastfood.foodAPI.api.assembler.FormaPagamentoModelAssembler;
import com.fastfood.foodAPI.api.model.FormaPagamentoDTO;
import com.fastfood.foodAPI.domain.Service.CadastroRestauranteService;
import com.fastfood.foodAPI.domain.model.FormaPagamento;
import com.fastfood.foodAPI.domain.model.Restaurante;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired 
	CadastroRestauranteService CadastroRestaurante;

	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId){
		
		Restaurante restaurante = CadastroRestaurante.BuscarOuFalhar(restauranteId);
		
		//na entidade restaurante formaPagamento está mapeado com "set" e "HashSet" então a lista tem que ser "set"
		Set<FormaPagamento> formaPagamento = restaurante.getFormaPagamento();
		
		return formaPagamentoModelAssembler.toCollectionModel(formaPagamento);
		
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		CadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		CadastroRestaurante.adicionarFormaPagamento(restauranteId, formaPagamentoId);
	}

	
	
}
