package br.com.daciosoftware.shop.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.daciosoftware.shop.modelos.dto.ShopDTO;
import br.com.daciosoftware.shop.shopping.service.ShoppingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	private ShoppingService shoppingService; 
	
	@GetMapping
	public List<ShopDTO> findAll() {		
		return shoppingService.findAll();
	}
	
	@GetMapping("/{id}")
	public ShopDTO findById(@PathVariable Long id) {
		return shoppingService.findById(id);
	}
	
	@GetMapping("/pageable")
	public Page<ShopDTO> findAllPageable(Pageable pageable) {
		return shoppingService.findAllPageable(pageable);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ShopDTO save(@Valid @RequestBody ShopDTO shopDTO) {		
		return shoppingService.save(shopDTO);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		shoppingService.delete(id);
	}
}
