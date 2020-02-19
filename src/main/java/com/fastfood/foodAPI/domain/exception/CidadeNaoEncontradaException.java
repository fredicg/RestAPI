package com.fastfood.foodAPI.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException (Long cidadeId) {
		this(String.format("Não existe um cadastro de cidade com o código %d", cidadeId));
	}
}
