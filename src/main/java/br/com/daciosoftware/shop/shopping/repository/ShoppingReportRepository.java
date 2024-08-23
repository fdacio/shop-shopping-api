package br.com.daciosoftware.shop.shopping.repository;

import java.time.LocalDate;
import java.util.List;

import br.com.daciosoftware.shop.modelos.dto.shopping.ShopSummaryReportDTO;
import br.com.daciosoftware.shop.modelos.dto.shopping.ShopUserReportDTO;
import br.com.daciosoftware.shop.modelos.entity.shopping.Shop;

public interface ShoppingReportRepository {

	List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo);
	ShopSummaryReportDTO getShopByDate(LocalDate dataInicio, LocalDate dataFim);
	List<ShopUserReportDTO> getShopUserByDate(LocalDate dataInicio, LocalDate dataFim);
}
