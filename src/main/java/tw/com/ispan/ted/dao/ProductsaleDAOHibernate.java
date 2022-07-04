package tw.com.ispan.ted.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import tw.com.ispan.ted.domain.ProductsaleBean;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class ProductsaleDAOHibernate implements ProductsaleDAO {

	@PersistenceContext
	private Session session = null;

	public Session getSession() {
		return session;
	}

	@Override
	public List<Object[]> select(Date fromDate, Date endDate) {
		//SELECT product_name, sum(quantity)
		//FROM `productsale`
		// WHERE create_date BETWEEN '2022-06-03 00:00:00' and '2022-06-05 23:59:59' GROUP by product_name;
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); //最後的結果型別

		Root<ProductsaleBean> root = criteriaQuery.from(ProductsaleBean.class);
		criteriaQuery.multiselect(root.get("productName"),criteriaBuilder.sum(root.get("quantity")));

		List<Predicate> conditionsList = new ArrayList<>();
		Predicate onStart = criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), fromDate);
		Predicate onEnd = criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), endDate);
		conditionsList.add(onStart);
		conditionsList.add(onEnd);
		criteriaQuery.where(conditionsList.toArray(new Predicate[]{}));

		criteriaQuery.groupBy(root.get("productName"));
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(root.get("quantity"))));

		TypedQuery<Object[]> typedQuery = this.getSession().createQuery(criteriaQuery);
		List<Object[]> result = typedQuery.getResultList();
		return result;
	}
}
