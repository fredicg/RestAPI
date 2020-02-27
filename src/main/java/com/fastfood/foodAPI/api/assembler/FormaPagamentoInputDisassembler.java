package com.fastfood.foodAPI.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.input.FormaPagamentoInput;
import com.fastfood.foodAPI.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {

	@Autowired
	public ModelMapper modelMapper;

	public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
		// usando ModelMapper
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}

	public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {

		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		// com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
		//cidade.setEstado(new Estado());

		modelMapper.map(formaPagamentoInput, formaPagamento);
	}

}
