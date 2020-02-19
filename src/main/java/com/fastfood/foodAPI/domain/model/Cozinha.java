package com.fastfood.foodAPI.domain.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fastfood.foodAPI.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {
	
	@NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	//Ignora o campo na resposta.
	//@JsonIgnore
	
	//altera o nome da propriedade na resposta
	//@JsonProperty("titulo")
	@NotBlank
	@Column(nullable = false)
	private String Nome;
	
	@OneToMany(mappedBy = "cozinha") 
	private List<Restaurante> restaurantes = new ArrayList<>(); 

}