package com.fastfood.foodAPI.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public GrupoNaoEncontradoException (Long grupoId) {
		this(String.format("Não existe um cadastro de grupo com o código %d", grupoId));
	}
}
