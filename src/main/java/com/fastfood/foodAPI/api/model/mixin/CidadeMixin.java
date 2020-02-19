package com.fastfood.foodAPI.api.model.mixin;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fastfood.foodAPI.domain.model.Estado;

public class CidadeMixin {
	
	
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "nome"})
	//para que o FetchType.LAZY funcione tem que ignorar o hibernateLazyInitializer
	private Estado estado;

}
