package com.fastfood.foodAPI.domain.Service;

import com.fastfood.foodAPI.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	
	
}
