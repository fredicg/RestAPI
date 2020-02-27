package com.fastfood.foodAPI.api.assembler;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.FormaPagamentoDTO;
import com.fastfood.foodAPI.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		
		//usando ModelMapper
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
													//para retornar "HashSet" como foi mapeado na entidade restaurante					
	public List<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> formaPagamentos) {
		return formaPagamentos.stream()
				.map(formaPagamento -> toModel(formaPagamento))
				.collect(Collectors.toList());
	}
	
}