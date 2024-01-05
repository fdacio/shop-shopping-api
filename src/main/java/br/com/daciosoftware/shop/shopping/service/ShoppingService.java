package br.com.daciosoftware.shop.shopping.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public ShopDTO save(ShopDTO shopDTO) {
		shopRepository.deleteAll();
		Float total = shopDTO.getItens().stream().map(i->i.getPreco()*i.getQuantidade()).reduce((float)0, Float::sum);
		shopDTO.setData(LocalDateTime.now());
		shopDTO.setTotal(total);
		Shop shop = Shop.convert(shopDTO); 
		shopRepository.save(shop);
		return ShopDTO.convert(shop);
	}

}
