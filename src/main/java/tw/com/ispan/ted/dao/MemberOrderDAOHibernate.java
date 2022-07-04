package tw.com.ispan.ted.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import tw.com.ispan.ted.domain.MemberOrderBean;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class MemberOrderDAOHibernate implements MemberOrderDAO {
	@PersistenceContext
	private Session session;

	public Session getSession() {
		return session;
	}

	@Override
	public MemberOrderBean select(String id) {
		System.out.println("DAO的order ID: " + id);
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<MemberOrderBean> criteriaQuery = criteriaBuilder.createQuery(MemberOrderBean.class);
		Root<MemberOrderBean> root = criteriaQuery.from(MemberOrderBean.class);
		Predicate p1 = criteriaBuilder.equal(root.get("orderId"), id);
		criteriaQuery.where(p1);
		TypedQuery<MemberOrderBean> typedQuery = this.getSession().createQuery(criteriaQuery);
		MemberOrderBean result = typedQuery.getSingleResult();
		if(result!=null) {
			return result;
		}
		return null;
	}

	public List<MemberOrderBean> select(Integer memberId, String status) {
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<MemberOrderBean> criteriaQuery = criteriaBuilder.createQuery(MemberOrderBean.class);

		//FROM product
		Root<MemberOrderBean> root = criteriaQuery.from(MemberOrderBean.class);

		Predicate p1,p2;
		try{
			if(!Objects.equals(memberId,0) || !Objects.equals(status, "00")){ //如果使用者有輸入會員名稱或訂單狀態才進來
				if(!Objects.equals(memberId, 0)){
					//memberId = ?
					p1 = criteriaBuilder.equal(root.get("memberId"), memberId);
					criteriaQuery.where(p1);
				}
				if(!Objects.equals(status, "00") && status.length()!=0){
					//status = ?
					p2 = criteriaBuilder.equal(root.get("status"),status);
					criteriaQuery.where(p2);
				}
				if(!Objects.equals(memberId, 0) && !Objects.equals(status, "00")){
					p1 = criteriaBuilder.equal(root.get("memberId"), memberId);
					p2 = criteriaBuilder.equal(root.get("status"),status);
					criteriaQuery.where(p1,p2);
				}
			}
		}catch (Exception e){
			System.out.println(e);
		}

		TypedQuery<MemberOrderBean> typedQuery = this.getSession().createQuery(criteriaQuery);
		List<MemberOrderBean> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
		}
		return null;
	}

	@Override
	public MemberOrderBean insert(MemberOrderBean bean) throws Exception {
		if(bean!=null && bean.getOrderId()!=null) {
			MemberOrderBean temp = this.getSession().get(MemberOrderBean.class, bean.getSeqno());
			if(temp==null) {
				System.out.println("null temp detected! so we do INSERT sql");
				this.getSession().save(bean);
				return bean;
			}
		}
		return null;
	}

	@Override
	public MemberOrderBean update(String name, Integer price,
								  String desc, String catalog, String id, Date updateDate, String updateUser) {
		if(id!=null) {
			MemberOrderBean temp = this.getSession().get(MemberOrderBean.class, id);
			if(temp!=null) {
				temp.setOrderId(name);




				return temp;
			}
		}
		return null;
	}

	@Override
	public boolean delete(String id) {
		if(id!=null) {
			MemberOrderBean temp = this.getSession().get(MemberOrderBean.class, id);
			if(temp!=null) {
				this.getSession().delete(temp);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deliver(MemberOrderBean bean) {
		if(bean!=null && bean.getOrderId()!=null) {
			MemberOrderBean temp = this.getSession().createNamedQuery("byOrderId",MemberOrderBean.class).setParameter("orderId",bean.getOrderId()).getSingleResult();
			temp.setUpdateDate(bean.getUpdateDate());
			temp.setDeliveredDate(bean.getUpdateDate());
			temp.setUpdateUser(bean.getUpdateUser());
			if(Objects.equals(temp.getStatus(), "01")){
				temp.setStatus("031"); //如果是貨到付款情況下出貨
			}else{
				temp.setStatus("03"); //03=已付款情況下出貨
			}
			this.getSession().save(temp);
			return true;
		}
		return false;
	}

	@Override
	public boolean receive(MemberOrderBean bean) {
		if(bean!=null && bean.getOrderId()!=null) {
			MemberOrderBean temp = this.getSession().createNamedQuery("byOrderId",MemberOrderBean.class).setParameter("orderId",bean.getOrderId()).getSingleResult();
			temp.setUpdateDate(bean.getUpdateDate());
			temp.setReceivedDate(bean.getUpdateDate());
			temp.setUpdateUser(bean.getUpdateUser());
			temp.setStatus("05"); //消費者取貨
			this.getSession().save(temp);
			return true;
		}
		return false;
	}

	@Override
	public boolean cancelOrder(MemberOrderBean bean) {
		if(bean!=null && bean.getOrderId()!=null) {
			MemberOrderBean temp = this.getSession().createNamedQuery("byOrderId",MemberOrderBean.class).setParameter("orderId",bean.getOrderId()).getSingleResult();
			temp.setUpdateDate(bean.getUpdateDate());
			temp.setUpdateUser(bean.getUpdateUser());
			temp.setStatus("04"); //取消訂單
			this.getSession().save(temp);
			return true;
		}
		return false;
	}
}
