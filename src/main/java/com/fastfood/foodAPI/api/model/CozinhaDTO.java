package com.fastfood.foodAPI.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.fastfood.foodAPI.api.model.view.RestauranteView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {

	@JsonView(RestauranteView.Resumo.class)
	private Long Id;
	
	@JsonView(RestauranteView.Resumo.class)
	private String Nome;
	
	
}
