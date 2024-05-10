package br.com.daciosoftware.shop.shopping.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daciosoftware.shop.modelos.dto.shopping.ShopDTO;
import br.com.daciosoftware.shop.modelos.dto.shopping.ShopReportDTO;
import br.com.daciosoftware.shop.shopping.repository.ShoppingReportRepositoryImpl;

@Service
public class ShoppingReportService {

	@Autowired
	private ShoppingReportRepositoryImpl shoppingReportRepository;
	
	public List<ShopDTO> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {
		
		return shoppingReportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo)
				.stream()
				.map(ShopDTO::convert)
				.collect(Collectors.toList());
	}
	
	public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {
		return shoppingReportRepository.getReportByDate(dataInicio, dataFim);
	}
}
