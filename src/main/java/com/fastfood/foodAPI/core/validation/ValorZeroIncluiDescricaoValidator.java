package com.fastfood.foodAPI.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	
@Override
public void initialize(ValorZeroIncluiDescricao constraint) {
	
	this.valorField = constraint.valorFiled();
	this.descricaoField = constraint.descricaoField();
	this.descricaoObrigatoria = constraint.descricaoObrigatorioa();
}
	
	@Override
	public boolean isValid(Object ObjetoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(ObjetoValidacao.getClass(), valorField)
					.getReadMethod().invoke(ObjetoValidacao);
			
			String descricao = (String) BeanUtils.getPropertyDescriptor(ObjetoValidacao.getClass(), descricaoField)
					.getReadMethod().invoke(ObjetoValidacao);
			
			if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
				valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ValidationException(e);
			
		}
		
		return valido;
	}

}
