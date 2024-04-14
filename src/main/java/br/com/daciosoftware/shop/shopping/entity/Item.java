package br.com.daciosoftware.shop.shopping.entity;

import br.com.daciosoftware.shop.product.entity.Product;
import br.com.daciosoftware.shop.shopping.dto.ItemDTO;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="itens")
@Table(name = "itens", schema = "shopping")
@Embeddable
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantidade;
	private Float preco;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="shop_id")
	private Shop shop;

	public static Item convert(ItemDTO itemDTO) {
		Item item = new Item();
		item.setId(itemDTO.getId());
		item.setProduct(Product.convert(itemDTO.getProduct()));
		item.setQuantidade(itemDTO.getQuantidade());
		item.setPreco(itemDTO.getPreco());
		return item;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", quantidade=" + quantidade + ", preco=" + preco + ", product=" + product + "]";
	}
	
}
