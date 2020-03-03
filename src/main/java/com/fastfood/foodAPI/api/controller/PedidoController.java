package com.fastfood.foodAPI.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastfood.foodAPI.api.assembler.PedidoInputDisassembler;
import com.fastfood.foodAPI.api.assembler.PedidoModelAssembler;
import com.fastfood.foodAPI.api.assembler.PedidoResumoModelAssembler;
import com.fastfood.foodAPI.api.model.PedidoDTO;
import com.fastfood.foodAPI.api.model.PedidoResumoDTO;
import com.fastfood.foodAPI.api.model.input.PedidoInput;
import com.fastfood.foodAPI.domain.Service.EmissaoPedidoService;
import com.fastfood.foodAPI.domain.exception.EntidadeNaoEncontradaException;
import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.model.Pedido;
import com.fastfood.foodAPI.domain.model.Usuario;
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
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@GetMapping
	public List<PedidoResumoDTO> listar() {
		List<Pedido> pedidos = pedidoRepository.findAll();
	
		/*for (Pedido pedido : pedidos) {
			System.out.println("pedido Id " +pedido.getId());
			System.out.println("pedido SubTotal " +pedido.getSubtotal());
			System.out.println("pedido frete " +pedido.getTaxa_frete());
			System.out.println("pedido total " +pedido.getValor_total());
			System.out.println("pedido status " +pedido.getStatus());
			System.out.println("pedido data criacao " +pedido.getData_criacao());
			System.out.println("pedido Restaurante Id " +pedido.getRestaurante().getId());
			System.out.println("pedido Restaurante Nome" +pedido.getRestaurante().getNome());
			System.out.println("pedido Restaurante Complemento " +pedido.getRestaurante().getEndereco().getComplemento());
			System.out.println("pedido Cliente Id " + pedido.getCliente().getId());
			System.out.println("pedido Cliente Nome " +pedido.getCliente().getNome());
			System.out.println("pedido Endereco Complemento " +pedido.getEnderecoEntrega().getComplemento());
		} */
		
		return pedidoResumoModelAssembler.toCollectionModel(pedidos);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			// TODO pegar usuário autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);
			
			novoPedido = emissaoPedido.emitir(novoPedido);
			
			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
		
		return pedidoModelAssembler.toModel(pedido);
	}
}
