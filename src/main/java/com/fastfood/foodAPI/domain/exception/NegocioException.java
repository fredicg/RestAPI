package com.fastfood.foodAPI.domain.exception;

public class NegocioException extends RuntimeException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public NegocioException(String mensagem) {
		super(mensagem);
	}
	
	public NegocioException (String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
