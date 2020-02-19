package com.fastfood.foodAPI.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fastfood.foodAPI.domain.model.Cozinha;
import com.fastfood.foodAPI.domain.model.Endereco;
import com.fastfood.foodAPI.domain.model.FormaPagamento;
import com.fastfood.foodAPI.domain.model.Produto;

public class RestauranteMixin {

		//@JsonIgnore
		//@JsonIgnoreProperties("hibernateLazyInitializer")
		@JsonIgnoreProperties(value = "nome", allowGetters = true)
		private Cozinha cozinha;
		
		@JsonIgnore
		private Endereco endereco;
		
		//@JsonIgnore
		private OffsetDateTime dataCadastro;
		
		//@JsonIgnore
		private OffsetDateTime dataAtualizacao;
		
		@JsonIgnore
		List<FormaPagamento> formaPagamento = new ArrayList<>();
		
		@JsonIgnore
		private List<Produto> produtos = new ArrayList<>(); 
	
}
