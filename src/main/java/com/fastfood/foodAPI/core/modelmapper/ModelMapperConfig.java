package com.fastfood.foodAPI.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fastfood.foodAPI.api.model.EnderecoDTO;
import com.fastfood.foodAPI.api.model.input.ItemPedidoInput;
import com.fastfood.foodAPI.domain.model.Endereco;
import com.fastfood.foodAPI.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {

		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), 
				(enderecoDTOdestino, value) -> enderecoDTOdestino.getCidade().setEstado(value));
		
		return modelMapper;
		
		
		
	}
	
}