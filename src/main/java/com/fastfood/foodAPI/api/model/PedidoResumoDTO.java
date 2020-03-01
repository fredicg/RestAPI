package com.fastfood.foodAPI.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoDTO {

	private Long id;
	private BigDecimal subTotal;
	private BigDecimal taxa_frete;
	private BigDecimal valor_total;
	private EnderecoDTO enderecoEntrega;
	private String status;
	private OffsetDateTime data_criacao;
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
	
}
