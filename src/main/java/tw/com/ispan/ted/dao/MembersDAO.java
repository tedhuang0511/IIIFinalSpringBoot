package tw.com.ispan.ted.dao;

import tw.com.ispan.ted.domain.MembersBean;

import java.util.Date;
import java.util.List;

public interface MembersDAO {

	public abstract MembersBean select(Integer memberId);

	public abstract MembersBean selectByAccount(String memberAccount);

	public abstract List<MembersBean> select();

	public abstract MembersBean insert(MembersBean bean);
	
	public abstract MembersBean update(String memberLastname, String memberFirstname,
			String memberNickname, String memberEmail, String memberTel,
			String memberAddr, Date memberBirth, Integer memberId);
		
	public abstract boolean delete(Integer memberId);

	MembersBean selectByEmail(String theEmail);
}
