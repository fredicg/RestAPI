package com.fastfood.foodAPI.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastfood.foodAPI.api.assembler.PedidoModelAssembler;
import com.fastfood.foodAPI.api.assembler.PedidoResumoModelAssembler;
import com.fastfood.foodAPI.api.model.PedidoDTO;
import com.fastfood.foodAPI.api.model.PedidoResumoDTO;
import com.fastfood.foodAPI.domain.Service.EmissaoPedidoService;
import com.fastfood.foodAPI.domain.model.Pedido;
import com.fastfood.foodAPI.domain.repository.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@GetMapping
	public List<PedidoResumoDTO> listar() {
		List<Pedido> todosPedidos = pedidoRepository.findAll();
	
		return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
		
		return pedidoModelAssembler.toModel(pedido);
	}
}
