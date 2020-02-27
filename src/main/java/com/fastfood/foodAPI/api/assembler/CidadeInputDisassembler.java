package com.fastfood.foodAPI.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastfood.foodAPI.api.model.input.CidadeInput;
import com.fastfood.foodAPI.domain.model.Cidade;
import com.fastfood.foodAPI.domain.model.Estado;

@Component
public class CidadeInputDisassembler {

	@Autowired
	public ModelMapper modelMapper;

	public Cidade toDomainObject(CidadeInput cidadeInput) {
		// usando ModelMapper
		return modelMapper.map(cidadeInput, Cidade.class);

		/*
		 * Restaurante restaurante = new Restaurante();
		 * restaurante.setNome(restauranteInput.getNome());
		 * restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		 * 
		 * Cozinha cozinha = new Cozinha();
		 * cozinha.setId(restauranteInput.getCozinha().getId());
		 * 
		 * restaurante.setCozinha(cozinha);
		 * 
		 * return restaurante;
		 */
	}

	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {

		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		// com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
		cidade.setEstado(new Estado());

		modelMapper.map(cidadeInput, cidade);
	}

}
