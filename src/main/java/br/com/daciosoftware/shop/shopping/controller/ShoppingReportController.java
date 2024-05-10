package br.com.daciosoftware.shop.shopping.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import br.com.daciosoftware.shop.modelos.dto.shopping.ShopDTO;
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
	public ResponseEntity<?> getReportByDate(
			@RequestParam(name = "dataInicio", required = true) 
			@DateTimeFormat(pattern = "dd/MM/yyyy")
			LocalDate dataInicio, 
			@RequestParam(name = "dataFim", required = true) 
			@DateTimeFormat(pattern = "dd/MM/yyyy")
			LocalDate dataFim) 
	{
		
		try {
			
	        ByteArrayOutputStream pdfStream = shoppingReportService.getReportByDate(dataInicio, dataFim);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=shop-resume.pdf");
	        headers.setContentLength(pdfStream.size());
	        
	        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
	        
		} catch (DocumentException | IOException | URISyntaxException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
		}
		
	}
}
