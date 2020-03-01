package com.fastfood.foodAPI.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {
	
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private BigDecimal subtotal;
	private BigDecimal taxa_frete;
	private BigDecimal valor_total;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime data_criacao;

	private OffsetDateTime data_confirmacao;
	private OffsetDateTime data_cancelamento;
	private OffsetDateTime data_entrega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name="usuario_cliente_id" ,nullable = false)
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();
	
	
	public void calcularValorTotal() {
		this.subtotal = getItens().stream()
			.map(item -> item.getPreco_total())
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		this.valor_total = this.subtotal.add(this.taxa_frete);
	}
	
	public void definirFrete() {
		setTaxa_frete(getRestaurante().getTaxaFrete());
	}
	
	public void atribuirPedidoAosItens() {
		getItens().forEach(item -> item.setPedido(this));
	}

}
