package br.com.daciosoftware.shop.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.daciosoftware.shop.modelos.entity.Shop;

@Repository
public interface ShoppingRepository extends JpaRepository<Shop, Long> {

}
