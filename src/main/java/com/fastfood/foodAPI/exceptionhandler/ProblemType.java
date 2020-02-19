package com.fastfood.foodAPI.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel","Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrada", "Recurso não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio","Violação de regra de negócio"),
	PARAMETRO_INVALIDO("/valor-invalido", "Valor informado inválido");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
	
		this.uri ="https://food-api.com" + path;
		this.title = title; 
		
	}
	
	
	

}
