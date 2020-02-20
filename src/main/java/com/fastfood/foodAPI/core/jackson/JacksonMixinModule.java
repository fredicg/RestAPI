package com.fastfood.foodAPI.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fastfood.foodAPI.api.model.mixin.CidadeMixin;
import com.fastfood.foodAPI.api.model.mixin.CozinhaMixin;
import com.fastfood.foodAPI.domain.model.Cidade;
import com.fastfood.foodAPI.domain.model.Cozinha;


@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}
	

}
