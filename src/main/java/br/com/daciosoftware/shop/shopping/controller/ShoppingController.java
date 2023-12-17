package br.com.daciosoftware.shop.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daciosoftware.shop.modelos.dto.ShopDTO;
import br.com.daciosoftware.shop.shopping.service.ShoppingService;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	private ShoppingService shoppingService; 
	
	@GetMapping
	public List<ShopDTO> findAll() {
		
		return shoppingService.findAll();
	}
	
}
