package br.com.daciosoftware.shop.shopping.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.daciosoftware.shop.exceptions.exceptions.ShopNotFoundException;
import br.com.daciosoftware.shop.modelos.dto.shopping.ItemDTO;
import br.com.daciosoftware.shop.modelos.dto.shopping.ShopDTO;
import br.com.daciosoftware.shop.modelos.dto.user.UserDTO;
import br.com.daciosoftware.shop.modelos.entity.shopping.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
		List<Shop> shops = shopRepository.findAll();
		
		return shops
				.stream()
				.map(ShopDTO::convert)
				.collect(Collectors.toList());
	}

	public ShopDTO findById(Long id) {
		return shopRepository.findById(id).map(ShopDTO::convert).orElseThrow(ShopNotFoundException::new);
	}

	public List<ShopDTO> findByUserIndentifier(Long userId) {
		List<Shop> shops = shopRepository.findByUserIdentifier(userId);
		return shops
				.stream()
				.map(ShopDTO::convert)
				.collect(Collectors.toList());
	}

	@Transactional
	public ShopDTO save(ShopDTO shopDTO, String key) {
		UserDTO userDTO = userService.validUserKey(shopDTO.getUser(), key);
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

	public Page<ShopDTO> findAllPageable(Pageable pageable) {
		return shopRepository.findAll(pageable).map(ShopDTO::convert);
	}
}
