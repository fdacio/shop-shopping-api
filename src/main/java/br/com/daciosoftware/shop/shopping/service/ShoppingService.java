package br.com.daciosoftware.shop.shopping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daciosoftware.shop.modelos.dto.ShopDTO;
import br.com.daciosoftware.shop.modelos.entity.Shop;
import br.com.daciosoftware.shop.shopping.repository.ShoppingRepository;

@Service
public class ShoppingService {

	@Autowired
	private ShoppingRepository shopRepository;
	
	public List<ShopDTO> findAll() {
		
		List<Shop> vendas = shopRepository.findAll();
		
		return vendas
				.stream()
				.map(ShopDTO::convert)
				.collect(Collectors.toList());
		
	}
}
