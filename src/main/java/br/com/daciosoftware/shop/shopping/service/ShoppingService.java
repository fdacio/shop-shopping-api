package br.com.daciosoftware.shop.shopping.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.daciosoftware.shop.exceptions.ShopNotFoundException;
import br.com.daciosoftware.shop.modelos.dto.ItemDTO;
import br.com.daciosoftware.shop.modelos.dto.ShopDTO;
import br.com.daciosoftware.shop.modelos.dto.UserDTO;
import br.com.daciosoftware.shop.modelos.entity.Shop;
import br.com.daciosoftware.shop.shopping.repository.ShoppingRepository;

@Service
public class ShoppingService {

	@Autowired
	private ShoppingRepository shopRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	
	public List<ShopDTO> findAll() {
		List<Shop> vendas = shopRepository.findAll();
		return vendas
				.stream()
				.map(ShopDTO::convert)
				.collect(Collectors.toList());
	}

	public ShopDTO findById(Long shopId) {
		Optional<Shop> shopOptional = shopRepository.findById(shopId);
		if (shopOptional.isPresent()) {
			return ShopDTO.convert(shopOptional.get());
		} else {
			throw new ShopNotFoundException();
		}
	}
	
	public Page<ShopDTO> findAllPageable(Pageable page) {
		Page<Shop> vendas = shopRepository.findAll(page);
		return vendas.map(ShopDTO::convert);
	}
	
	@Transactional
	public ShopDTO save(ShopDTO shopDTO) {		
		UserDTO userDTO = userService.findById(shopDTO.getUser().getId());
		List<ItemDTO> itensDTO = productService.findItens(shopDTO);
		Float total = itensDTO.stream().map(i -> (i.getPreco()*i.getQuantidade()) ).reduce((float)0, Float::sum);
		
		shopDTO.setData(LocalDateTime.now());
		shopDTO.setTotal(total);
		shopDTO.setUser(userDTO);
		shopDTO.setItens(itensDTO);
		
		Shop shop = Shop.convert(shopDTO);
		shop = shopRepository.save(shop);
		
		return ShopDTO.convert(shop);
	}
	
	public void delete (Long shopId) {
		Optional<Shop> shopOptional = shopRepository.findById(shopId);
		if (shopOptional.isPresent()) {
			shopRepository.delete(shopOptional.get());
		} else {
			throw new RuntimeException("Venda não encontrada");
		}
	}

}
