package com.fastfood.foodAPI.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public UsuarioNaoEncontradoException (Long usuarioId) {
		this(String.format("Não existe um cadastro de usuário com o código %d", usuarioId));
	}
}
