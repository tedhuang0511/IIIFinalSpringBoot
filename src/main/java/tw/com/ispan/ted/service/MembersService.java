package tw.com.ispan.ted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.ted.dao.MembersDAO;
import tw.com.ispan.ted.domain.MembersBean;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class MembersService {
	@Autowired
	private MembersDAO membersDao;

	@Transactional(readOnly = true)
	public MembersBean login(String memberAccount, String password) {
		//依據輸入的memberAccount去找相對應的bean
		MembersBean bean = membersDao.selectByAccount(memberAccount);

		if(bean!=null) {
			if(password!=null) {
				//pass為資料庫抓出來正確的密碼
				String pass = bean.getMemberPassword();
				//驗證輸入的密碼是否相等資料庫抓出來的密碼
				if(pass.equals(password)) {
					return bean;
				}
			}
		}
		return null;
	}

	
	public List<MembersBean> select(MembersBean bean){
		List<MembersBean> result = null;
		if(bean!=null && bean.getMemberId()!=null && !bean.getMemberId().equals(0)) {
			MembersBean temp = membersDao.select(bean.getMemberId());
			if(temp!=null) {
				result = new ArrayList<MembersBean>();
				result.add(temp);
			}
		}else {
			result = membersDao.select();
		}	
		return result;
	};
	public MembersBean insert(MembersBean bean){
		MembersBean result = null;
		if(bean!=null) {
			System.out.println(bean.getMemberId() + " from Members service");
			result = membersDao.insert(bean);
		}	
		return result;
	};
	
	public MembersBean update(MembersBean bean){
		MembersBean result = null;
		if(bean!=null && bean.getMemberId()!=null) {
			result = membersDao.update(bean.getMemberLastname(),
					bean.getMemberFirstname(),
					bean.getMemberNickname(),
					bean.getMemberEmail(),
					bean.getMemberTel(),
					bean.getMemberAddr(),
					bean.getMemberBirth(),
					bean.getMemberId());				
		}	
		return result;
	};
	
	public boolean delete(MembersBean bean) {
		boolean result = false;
		if(bean!=null && bean.getMemberId()!=null) {
			result = membersDao.delete(bean.getMemberId());
		}
		return result;
	};

	public MembersBean selectByEmail(String theEmail) {
		MembersBean theMembersBean = membersDao.selectByEmail(theEmail);
		if(theMembersBean == null) {
			return null;
		}
		else {
			return theMembersBean;
		}
		
	}
	

}
