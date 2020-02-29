package com.fastfood.foodAPI.domain.exception;

public class PermissaoNaoEncontradoException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public PermissaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PermissaoNaoEncontradoException (Long permissaoId) {
		this(String.format("Não existe um cadastro de permissão com o código %d", permissaoId));
	}
}
