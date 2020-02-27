package com.fastfood.foodAPI.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.input.RestauranteInput;
import com.fastfood.foodAPI.domain.model.Cidade;
import com.fastfood.foodAPI.domain.model.Cozinha;
import com.fastfood.foodAPI.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	public ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		//usando ModelMapper
		return modelMapper.map(restauranteInput, Restaurante.class);
		
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		//para evitar : org.hibernate.HibernateException: identifier of an instance of 
		//com.fastfood.foodAPI.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		if (restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}
	
}
