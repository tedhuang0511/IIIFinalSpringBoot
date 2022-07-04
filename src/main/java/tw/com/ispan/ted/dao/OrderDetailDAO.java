package tw.com.ispan.ted.dao;

import tw.com.ispan.ted.domain.OrderDetailBean;

import java.util.List;

public interface OrderDetailDAO {
    List<OrderDetailBean> select(String id);

    Integer insert(OrderDetailBean bean) throws Exception;
}
