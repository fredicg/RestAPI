package com.fastfood.foodAPI.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.input.PermissaoInput;
import com.fastfood.foodAPI.domain.model.Permissao;

@Component
public class PermissaoInputDisassembler {

	@Autowired
	public ModelMapper modelMapper;
	
	public Permissao toDomainObject(PermissaoInput permissaoInput) {
		//usando ModelMapper
		return modelMapper.map(permissaoInput, Permissao.class);
		
	}
	
	public void copyToDomainObject(PermissaoInput permissaoInput, Permissao permissao) {
		//para evitar : org.hibernate.HibernateException: identifier of an instance of 
		//com.fastfood.foodAPI.domain.model.Cozinha was altered from 1 to 2
		//restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(permissaoInput, permissao);
	}
	
}
