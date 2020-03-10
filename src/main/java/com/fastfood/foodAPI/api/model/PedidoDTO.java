package com.fastfood.foodAPI.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

	private String codigo;
	private BigDecimal subTotal;
	private BigDecimal taxa_frete;
	private BigDecimal valor_total;
	private EnderecoDTO enderecoEntrega;
	private String status;
	private OffsetDateTime data_criacao;
	private OffsetDateTime data_confirmacao;
	private OffsetDateTime data_cancelamento;
	private OffsetDateTime data_entrega;
	private FormaPagamentoDTO formaPagamento;
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
	private List<ItemPedidoDTO> itens; 
	
	
	
}
