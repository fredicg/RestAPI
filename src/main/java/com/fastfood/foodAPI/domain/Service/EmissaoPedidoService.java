package com.fastfood.foodAPI.domain.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastfood.foodAPI.domain.exception.PedidoNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Pedido;
import com.fastfood.foodAPI.domain.repository.PedidoRepository;


@Service
public class EmissaoPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
			.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
}
