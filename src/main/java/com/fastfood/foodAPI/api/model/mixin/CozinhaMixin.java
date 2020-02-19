package com.fastfood.foodAPI.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fastfood.foodAPI.domain.model.Restaurante;

public class CozinhaMixin {

	//ignora a serialização de restaurantes
	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>(); 

}
