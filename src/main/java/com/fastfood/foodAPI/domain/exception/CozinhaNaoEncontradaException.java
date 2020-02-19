package com.fastfood.foodAPI.domain.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada.")
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException (Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com o código %d", cozinhaId));
	}
}
