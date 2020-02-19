package com.fastfood.foodAPI.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoEncontradoException (Long restauranteId) {
		this(String.format("Não existe um cadastro de restaurante com o código %d", restauranteId));
	}
}
