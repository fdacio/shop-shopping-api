package br.com.daciosoftware.shop.shopping.repository;

import java.time.LocalDate;
import java.util.List;

import br.com.daciosoftware.shop.modelos.dto.shopping.ShopReportDTO;
import br.com.daciosoftware.shop.modelos.entity.shopping.Shop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ShoppingReportRepositoryImpl implements ShoppingReportRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {
		
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select s from shop s \n");
		sbSql.append("where s.data >= :dataInicio \n");
		
		if (dataFim != null) {
			sbSql.append("and s.data <= :dataFim \n");
		}
		
		if (valorMinimo != null) {
			sbSql.append("and s.total <= :valorMinimo \n");
		}
		
		sbSql.append("order by s.data");
		
		TypedQuery<Shop> query = entityManager.createQuery(sbSql.toString(), Shop.class);
		
		query.setParameter("dataInicio", dataInicio.atTime(0, 0));
		
		if (dataFim != null) {
			query.setParameter("dataFim", dataFim.atTime(23, 59));
		}
		
		if (valorMinimo != null) {
			query.setParameter("valorMinimo", valorMinimo);
		}
		
	    return query.getResultList();
	    
	}
	
	@Override
	public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {

		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select \n");
		sbSql.append("count(s.id) as count, \n");
		sbSql.append("sum(s.total) as total, \n");
		sbSql.append("avg(s.total) as mean \n");
		sbSql.append("from shop s \n");
		sbSql.append("where s.data >= :dataInicio \n");
		sbSql.append("and s.data <= :dataFim \n");
		
		Query query = entityManager.createQuery(sbSql.toString());
		query.setParameter("dataInicio", dataInicio.atTime(0, 0));
		query.setParameter("dataFim", dataFim.atTime(23, 59));
		
		Object[] result = (Object[]) query.getSingleResult();
		
		Integer count = ((Long) result[0]).intValue();
		Float total = ((Double) result[1]).floatValue();
		Float mean = ((Double) result[2]).floatValue();
		
		ShopReportDTO shopReportDTO = new ShopReportDTO();
		shopReportDTO.setCount(count);
		shopReportDTO.setTotal(total);
		shopReportDTO.setMean(mean);
		
		return shopReportDTO;
		
	}

}
