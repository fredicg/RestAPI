package com.fastfood.foodAPI.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fastfood.foodAPI.core.validation.Multiplo;
import com.fastfood.foodAPI.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ValorZeroIncluiDescricao(valorFiled="taxaFrete", descricaoField="nome", descricaoObrigatorioa="Frete Grátis")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	//@NotEmpty
	//@NotBlank//(groups = Groups.CadastroRestaurante.class)
	//caso o frete seja zero(0) o campo nome de conter Frete Grátis 
	@Column(nullable = false)
	private String nome;
	 
	//@NotNull
	//@PositiveOrZero
	@Multiplo(numero = 5)
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.FALSE;
	//@JsonIgnore
	//@JsonIgnoreProperties("hibernateLazyInitializer")
	//@JsonIgnoreProperties(value = "nome", allowGetters = true)
	//@Valid
	//@ConvertGroup(from= Default.class, to = Groups.CozinhaId.class)
	//@NotNull
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name="cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name="restaurante_forma_pagamento", joinColumns = @JoinColumn(name="restaurante_id"),
	inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
	//assim inpede a inserção de forma de pagamento duplicado
	private Set<FormaPagamento> formaPagamento = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurante") 
	private List<Produto> produtos = new ArrayList<>(); 
	
	public void ativar() {
		setAtivo(true);
	}
	public void inativar() {
		setAtivo(false);
	}
	
	public void abrir() {
		setAberto(true);
	}
	
	public void fechar() {
		setAberto(false);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormaPagamento().add(formaPagamento);
	}
	
	public boolean desassociarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormaPagamento().remove(formaPagamento);
	}
	
}
