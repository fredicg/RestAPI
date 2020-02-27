package com.fastfood.foodAPI.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.input.UsuarioInput;
import com.fastfood.foodAPI.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	public ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioInput usuarioInput) {
		//usando ModelMapper
		return modelMapper.map(usuarioInput, Usuario.class);
		
	}
	
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
		//para evitar : org.hibernate.HibernateException: identifier of an instance of 
		//com.fastfood.foodAPI.domain.model.Cozinha was altered from 1 to 2
		//restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(usuarioInput, usuario);
	}
	
}
