package com.fastfood.foodAPI.api.assembler;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.PermissaoDTO;
import com.fastfood.foodAPI.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PermissaoDTO toModel(Permissao permissao) {
		
		//usando ModelMapper
		return modelMapper.map(permissao, PermissaoDTO.class);
	}
	
	public List<PermissaoDTO> toCollectionModel(Collection<Permissao> permissoes) {
		return permissoes.stream()
				.map(permissao -> toModel(permissao))
				.collect(Collectors.toList());
	}
	
}