package com.fastfood.foodAPI.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.fastfood.foodAPI.core.data.PageableTranslator;
import com.fastfood.foodAPI.domain.Service.EmissaoPedidoService;
import com.fastfood.foodAPI.domain.exception.EntidadeNaoEncontradaException;
import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.filter.PedidoFilter;
import com.fastfood.foodAPI.domain.model.Pedido;
import com.fastfood.foodAPI.domain.model.Usuario;
import com.fastfood.foodAPI.domain.repository.PedidoRepository;
import com.fastfood.foodAPI.infra.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;

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
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro,@PageableDefault(size=2) Pageable pageable ) {
		
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
	
		List<PedidoResumoDTO> pedidoResumoDTO = pedidoResumoModelAssembler.toCollectionModel(pedidosPage.getContent());
		
		Page<PedidoResumoDTO> pedidosResumoDTOPage = new PageImpl<>(pedidoResumoDTO, pageable, pedidosPage.getTotalElements());
		
		return pedidosResumoDTOPage;
		
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
	
	
	@GetMapping("/{codigopedido}")
	public PedidoDTO buscar(@PathVariable String codigopedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigopedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	private Pageable  traduzirPageable(Pageable apiPageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"cliente.nome", "cliente.nome",
				"valor_total", "valor_total"
				);
		return PageableTranslator.translate(apiPageable, mapeamento);
		
	}
}
