package com.fastfood.foodAPI.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.input.GrupoInput;
import com.fastfood.foodAPI.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {

	@Autowired
	public ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInput grupoInput) {
		//usando ModelMapper
		return modelMapper.map(grupoInput, Grupo.class);
		
	}
	
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		//para evitar : org.hibernate.HibernateException: identifier of an instance of 
		//com.fastfood.foodAPI.domain.model.Cozinha was altered from 1 to 2
		//restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(grupoInput, grupo);
	}
	
}
