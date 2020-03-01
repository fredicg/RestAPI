package com.fastfood.foodAPI.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDTO {

	private Long produtoId;
	private String produtoNome;
	private Integer quantidade;
	private BigDecimal preco_unitario;
	private BigDecimal preco_total;
	private String observacao;
	
}