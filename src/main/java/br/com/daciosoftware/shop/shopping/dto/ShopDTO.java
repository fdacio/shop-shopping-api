package br.com.daciosoftware.shop.shopping.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.daciosoftware.shop.shopping.entity.Shop;
import br.com.daciosoftware.shop.user.dto.UserDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {

	private Long id;
	private LocalDateTime data;
	private Float total;
	@NotNull(message="Informe o usuário")
	private UserDTO user;
	@NotNull(message="Informe os itens")
	private List<ItemDTO> itens = new ArrayList<>();
	
	public static ShopDTO convert(Shop shop) {
		ShopDTO shopDTO = new ShopDTO();
		shopDTO.setId(shop.getId());
		shopDTO.setData(shop.getData());
		shopDTO.setTotal(shop.getTotal());
		shopDTO.setUser(UserDTO.convert(shop.getUser()));
		List<ItemDTO> itensDTO = shop.getItens().stream().map(ItemDTO::convert).collect(Collectors.toList());
		shopDTO.setItens(itensDTO);
		return shopDTO;
	}

	@Override
	public String toString() {
		return "ShopDTO [id=" + id + ", data=" + data + ", total=" + total + ", user=" + user + ", itens=" + itens
				+ "]";
	}
	
}
