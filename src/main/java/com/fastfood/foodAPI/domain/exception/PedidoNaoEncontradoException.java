package com.fastfood.foodAPI.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PedidoNaoEncontradoException (Long pedidoId) {
		this(String.format("Não existe um pedido com o código %d", pedidoId));
	}
}
