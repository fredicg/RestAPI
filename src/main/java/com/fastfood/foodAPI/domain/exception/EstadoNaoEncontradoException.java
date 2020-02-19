package com.fastfood.foodAPI.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoException (Long estadoId) {
		this(String.format("Não existe um cadastro de estado com o código %d", estadoId));
	}
}
