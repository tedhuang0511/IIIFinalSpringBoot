package tw.com.ispan.ted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tw.com.ispan.ted.dao.MemberRepository;
import tw.com.ispan.ted.domain.MembersBean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService2 {
    Date date = new Date();
    Timestamp timestamp = new Timestamp(date.getTime());

    @Autowired
    MemberRepository memberRepository;

    public MembersBean CryptLogin(String memberAccouunt, String passwd) {
        List<MembersBean> result = memberRepository.findByMemberAccouunt(memberAccouunt);
        System.out.println("帳號查詢結果 = " + result.toString());
        if (!result.isEmpty()) {
            String DBPasswd = result.get(0).getMemberPassword();
//        如果密碼符合就吐回bean
            if (BCrypt.checkpw(passwd, DBPasswd)) {
                return result.get(0);
            }
        }
        //找不到帳號或密碼不符合
        return null;
    }


    public MembersBean CryptInsert(MembersBean bean) {
        MembersBean result = null;

        //controller要先設定beanID=0
        if (bean != null && bean.getMemberId() != null) {
            String OriginPW = bean.getMemberPassword();
            String CryptPW = BCrypt.hashpw(OriginPW, BCrypt.gensalt());

            //Todo設條件
            bean.setMemberPassword(CryptPW);
            bean.setCreateUser("");
            bean.setUpdateUser("");

            //設定為現在時間
            bean.setCreateDate(timestamp);
            bean.setUpdateDate(timestamp);

            //確認資料庫是否有重複的id
            Optional<MembersBean> optional = memberRepository.findById(bean.getMemberId());
            //若資料庫沒有才可以新增
            if (optional.isEmpty()) {
                return memberRepository.save(bean);
            }
        }

        return null;
    }

    ;


    public MembersBean findByAccount(String account) {
        MembersBean bean = (memberRepository.findByMemberAccouunt(account)).get(0);

        if (bean != null) {
            return bean;
        }
        return null;
    }

    ;

    public MembersBean changePassword(MembersBean bean, String newPasswd) {
        if (bean != null) {
            bean.setMemberPassword(BCrypt.hashpw(newPasswd,BCrypt.gensalt()));
            System.out.println("return memberRepository.save(bean)成功");
            return memberRepository.save(bean);
        }
        System.out.println("return null");
        return null;
    }


}
