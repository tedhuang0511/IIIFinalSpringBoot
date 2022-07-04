package tw.com.ispan.ted.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tw.com.ispan.ted.domain.MembersBean;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;


@Repository
public class MembersDAOHibernate implements MembersDAO {
	@PersistenceContext
	private Session session = null;

	public Session getSession() {
		return session;
	}

	@Override
	public MembersBean select(Integer memberId) {
		if (memberId != null) {
			return this.getSession().get(MembersBean.class, memberId);
		}
		return null;
	}


	@Override
	public MembersBean selectByAccount(String memberAccount){
		// select * from members where member_accouunt LIKE "apple123";
		if(memberAccount!=null && memberAccount.length()!=0){
			CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
			CriteriaQuery<MembersBean> criteriaQuery = criteriaBuilder.createQuery(MembersBean.class);
			Root<MembersBean> root = criteriaQuery.from(MembersBean.class);
			criteriaQuery = criteriaQuery.where(criteriaBuilder.like(root.get("memberAccouunt"),memberAccount));

			TypedQuery<MembersBean> typedQuery = this.getSession().createQuery(criteriaQuery);

			//memberAccount只是唯一的不可能抓出List
//			List<MembersBean> result = typedQuery.getResultList();
//			for(MembersBean whateverBean:result){
//				System.out.println("whateverBean.getMemberId() =" + whateverBean.getMemberId());
//			}

			try{
				MembersBean beanSelectByAccount = typedQuery.getSingleResult();
				System.out.println("beanSelectByAccount.getMemberAccouunt() = " + beanSelectByAccount.getMemberAccouunt());
				System.out.println("beanSelectByAccount.getMemberPassword() = " + beanSelectByAccount.getMemberPassword());

				return beanSelectByAccount;
			}catch (Exception e){
				System.out.println("typedQuery.getSingleResult() is null");
				return null;
			}
		}
		return null;
	}
	@Override
	public List<MembersBean> select() {
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<MembersBean> criteriaQuery = criteriaBuilder.createQuery(MembersBean.class);
//		Root<MembersBean> root = criteriaQuery.from(MembersBean.class);//??
		TypedQuery<MembersBean> typedQuery = this.getSession().createQuery(criteriaQuery);
		List<MembersBean> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
		}
		return null;
	}

	@Override
	public MembersBean insert(MembersBean bean) {
		if (bean != null && bean.getMemberId() != null) {
			MembersBean temp = this.getSession().get(MembersBean.class, bean.getMemberId());
			if (temp == null) {
				if(bean.getMemberLastname()==null) {
					bean.setMemberLastname("");
				};
				if(bean.getMemberFirstname()==null) {
					bean.setMemberFirstname("");
				};
				if(bean.getMemberAddr()==null) {
					bean.setMemberAddr("");
				};
				if(bean.getMemberGender()==null) {
					bean.setMemberGender("female");
				};
				if(bean.getCreateDate()==null) {
					bean.setCreateDate(new Date(11, 11, 11));
				};
				if(bean.getUpdateDate()==null) {
					bean.setUpdateDate(new Date(11, 11, 11));
				};
				if(bean.getCreateUser()==null) {
					bean.setCreateUser("");
				};
				if(bean.getUpdateUser()==null) {
					bean.setUpdateUser("");
				};
				if(bean.getMemberAccouunt()==null) {
					bean.setMemberAccouunt("");
				};
				if(bean.getMemberBirth()==null) {
					bean.setMemberBirth(new Date(11, 11, 11));
				};
				if(bean.getMemberNickname()==null) {
					bean.setMemberNickname("");
				};
				if(bean.getMemberEmail()==null) {
					bean.setMemberEmail("");
				};
				if(bean.getMemberPassword()==null) {
					bean.setMemberPassword("");
				};
				if(bean.getMemberTel()==null) {
					bean.setMemberTel("");
				};
				
						
				this.getSession().save(bean);
				return bean;
			}
		}
		return null;
	}
	public MembersBean update(MembersBean bean) {
		if(bean!=null && bean.getMemberId()!=null) {
			MembersBean temp = this.getSession().get(MembersBean.class, bean.getMemberId());
			if(temp!=null) {
				return (MembersBean) this.getSession().merge(bean);
			}
		}
		return null;
	}
	@Override
	public MembersBean update(String memberLastname, String memberFirstname, String memberNickname, String memberEmail,
			String memberTel, String memberAddr, Date memberBirth,Integer memberId) {
		if (memberId != null) {
			MembersBean temp = this.getSession().get(MembersBean.class, memberId);
			if (temp != null) {
				temp.setMemberLastname(memberLastname);
				temp.setMemberFirstname(memberFirstname);
				temp.setMemberNickname(memberNickname);
				temp.setMemberTel(memberTel);
				temp.setMemberAddr(memberAddr);
				temp.setMemberBirth(memberBirth);
				
				return temp;
			}
		}
		return null;
	}

	@Override
	public boolean delete(Integer memberId) {
		if (memberId != null) {
			MembersBean temp = this.getSession().get(MembersBean.class, memberId);
			if (temp != null) {
				this.getSession().delete(temp);
				return true;
			}
		}
		return false;
	}

	@Override
	public MembersBean selectByEmail(String theEmail) {
		Session currentSession = this.getSession();
		Query theQuery = currentSession.createQuery("from MembersBean where member_email =:memberEmail");
        theQuery.setParameter("memberEmail", theEmail);

        MembersBean theMember = (MembersBean) theQuery.uniqueResult();
        return theMember;

	}

}
