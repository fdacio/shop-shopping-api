package br.com.daciosoftware.shop.shopping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.daciosoftware.shop.modelos.dto.ItemDTO;
import br.com.daciosoftware.shop.modelos.dto.ProductDTO;
import br.com.daciosoftware.shop.modelos.dto.ShopDTO;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Value("${product.api.url}")
	private String productApiURL;
	
	public List<ItemDTO> findItens(ShopDTO shopDTO) {
		
		List<ItemDTO> itensDTO = new ArrayList<>();
		
		WebClient webClient = WebClient.builder()
				.baseUrl(productApiURL)
				.build();
		
		for (ItemDTO i : shopDTO.getItens()) {
			try {
				Mono<ProductDTO> product = webClient.get().uri("/product/"+i.getProduct().getId()).retrieve().bodyToMono(ProductDTO.class);
				ProductDTO productDTO = product.block();
				i.setProduct(productDTO);
				i.setPreco(productDTO.getPreco());
				itensDTO.add(i);
			} catch (Exception e) {
				throw new RuntimeException("Produto não encontrado");
			}
		}

		return itensDTO; 
	}
}
