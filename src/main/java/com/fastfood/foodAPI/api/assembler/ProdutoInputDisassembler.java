package com.fastfood.foodAPI.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.input.ProdutoInput;
import com.fastfood.foodAPI.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {

	@Autowired
	public ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoInput produtoInput) {
		//usando ModelMapper
		return modelMapper.map(produtoInput, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
		
		modelMapper.map(produtoInput, produto);
	}
	
}
