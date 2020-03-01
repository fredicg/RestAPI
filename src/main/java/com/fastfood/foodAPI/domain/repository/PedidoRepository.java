package com.fastfood.foodAPI.domain.repository;

import org.springframework.stereotype.Repository;

import com.fastfood.foodAPI.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

	

}
