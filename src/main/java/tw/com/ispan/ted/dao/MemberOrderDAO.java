package tw.com.ispan.ted.dao;

import tw.com.ispan.ted.domain.MemberOrderBean;

import java.util.Date;
import java.util.List;

public interface MemberOrderDAO {
    MemberOrderBean select(String id);

    List<MemberOrderBean> select(Integer memberId , String status);

    MemberOrderBean insert(MemberOrderBean bean) throws Exception;

    MemberOrderBean update(String name, Integer price,
                           String desc, String catalog, String id, Date updateDate, String updateUser);

    boolean delete(String id);

    boolean deliver(MemberOrderBean bean);
    boolean receive(MemberOrderBean bean);
    boolean cancelOrder(MemberOrderBean bean);
}
