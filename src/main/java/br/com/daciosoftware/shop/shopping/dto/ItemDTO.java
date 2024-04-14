package br.com.daciosoftware.shop.shopping.dto;

import br.com.daciosoftware.shop.product.dto.ProductDTO;
import br.com.daciosoftware.shop.shopping.entity.Item;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
	
	private Long id;
	@NotNull(message="Informe a quantidade")
	private Integer quantidade;
	@NotNull(message="Informe a preco do produto")
	private Float preco;
	@NotNull(message="Informe o produto")
	private ProductDTO product;
	
	public static ItemDTO convert(Item item) {
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(item.getId());
		itemDTO.setProduct(ProductDTO.convert(item.getProduct()));
		itemDTO.setQuantidade(item.getQuantidade());
		itemDTO.setPreco(item.getPreco());
		return itemDTO;
	}

	@Override
	public String toString() {
		return "ItemDTO [product=" + product + ", quantidade=" + quantidade + ", preco=" + preco + "]";
	}
	
}
