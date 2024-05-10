package br.com.daciosoftware.shop.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.daciosoftware.shop.modelos.entity.shopping.Shop;


@Repository
public interface ShoppingRepository extends JpaRepository<Shop, Long>, ShoppingReportRepository {

	@Query("select s from shop s where s.user.id = :userId")
	List<Shop> findByUserIndentifier(Long userId);
}
