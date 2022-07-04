package tw.com.ispan.ted.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import tw.com.ispan.ted.domain.OrdersumBean;

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
public class OrdersumDAOHibernate implements OrdersumDAO {

	@PersistenceContext
	private Session session = null;

	public Session getSession() {
		return session;
	}

	@Override
	public List<OrdersumBean> select(Date fromDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<OrdersumBean> criteriaQuery = criteriaBuilder.createQuery(OrdersumBean.class); //最後的結果型別

		Root<OrdersumBean> root = criteriaQuery.from(OrdersumBean.class);

		List<Predicate> conditionsList = new ArrayList<>();
		Predicate onStart = criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), fromDate);
		Predicate onEnd = criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), endDate);
		conditionsList.add(onStart);
		conditionsList.add(onEnd);
		criteriaQuery.where(conditionsList.toArray(new Predicate[]{}));

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));

		TypedQuery<OrdersumBean> typedQuery = this.getSession().createQuery(criteriaQuery);
		List<OrdersumBean> result = typedQuery.getResultList();
		return result;
	}
}
