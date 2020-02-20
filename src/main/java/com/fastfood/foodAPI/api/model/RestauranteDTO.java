package com.fastfood.foodAPI.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {

	private Long id;
	private String nome;
	private BigDecimal TaxaFrete;
	private CozinhaDTO cozinha;
	
}
