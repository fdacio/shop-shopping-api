package br.com.daciosoftware.shop.shopping.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.daciosoftware.shop.modelos.dto.shopping.ShopDTO;
import br.com.daciosoftware.shop.modelos.dto.shopping.ShopReportDTO;
import br.com.daciosoftware.shop.shopping.service.ShoppingReportService;

@RestController
@RequestMapping("/shopping/report")
public class ShoppingReportController {

	@Autowired
	ShoppingReportService shoppingReportService;
	
	@GetMapping("/filters")
	public List<ShopDTO> getShopByFilters(
			@RequestParam(name = "dataInicio", required = true) 
			@DateTimeFormat(pattern = "dd/MM/yyyy")
			LocalDate dataInicio, 
			@RequestParam(name = "dataFim", required = false) 
			@DateTimeFormat(pattern = "dd/MM/yyyy")
			LocalDate dataFim, 
			@RequestParam(name = "valorMinimo", required = false)
			Float valorMinimo) 
	{
		
		
		return shoppingReportService.getShopByFilters(dataInicio, dataFim, valorMinimo);
	}
	
	@GetMapping("/periodo")
	public ShopReportDTO getReportByDate(
			@RequestParam(name = "dataInicio", required = true) 
			@DateTimeFormat(pattern = "dd/MM/yyyy")
			LocalDate dataInicio, 
			@RequestParam(name = "dataFim", required = true) 
			@DateTimeFormat(pattern = "dd/MM/yyyy")
			LocalDate dataFim) 
	{
		return shoppingReportService.getReportByDate(dataInicio, dataFim);
	}
}
