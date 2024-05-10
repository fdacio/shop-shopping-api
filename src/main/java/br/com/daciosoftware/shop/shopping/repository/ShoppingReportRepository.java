package br.com.daciosoftware.shop.shopping.repository;

import java.time.LocalDate;
import java.util.List;

import br.com.daciosoftware.shop.modelos.dto.shopping.ShopReportDTO;
import br.com.daciosoftware.shop.modelos.entity.shopping.Shop;

public interface ShoppingReportRepository {

	public List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo);
	public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim);
}
