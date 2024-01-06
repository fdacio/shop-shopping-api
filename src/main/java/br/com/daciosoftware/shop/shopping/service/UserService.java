package br.com.daciosoftware.shop.shopping.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.daciosoftware.shop.modelos.dto.UserDTO;
import reactor.core.publisher.Mono;

@Service
public class UserService {
	
	@Value("${user.api.url}")
	private String userApiURL;
	
	public UserDTO findById(Long id) {
		try {
			WebClient webClient = WebClient.builder()
					.baseUrl(userApiURL)
					.build();
			Mono<UserDTO> user = webClient.get().uri("/user/"+id).retrieve().bodyToMono(UserDTO.class);
			return user.block();
			
		} catch (Exception e) {
			throw new RuntimeException("Usuário não encontrado");
		}
	}
	

}
