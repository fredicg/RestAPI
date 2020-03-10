package com.fastfood.foodAPI.domain.Service;

import java.util.List;

import com.fastfood.foodAPI.domain.filter.VendaDiariaFilter;
import com.fastfood.foodAPI.domain.model.dto.VendaDiaria;


public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	
}
