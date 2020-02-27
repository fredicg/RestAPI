package com.fastfood.foodAPI.domain.exception;

public class ProdutoNaoEncontradaException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public ProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public ProdutoNaoEncontradaException (Long restauranteId ,Long produtoId) {
		this(String.format("Não existe um cadastro de produto com o código %d", produtoId));
	}
}
