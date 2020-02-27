package com.fastfood.foodAPI.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{//ResponseStatusException  {

	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
	 * super(status, reason); }
	 */
	public FormaPagamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradoException (Long formaPagamentoId) {
		this(String.format("Não existe um cadastro de forma de pagamento com o código %d", formaPagamentoId));
	}
}
