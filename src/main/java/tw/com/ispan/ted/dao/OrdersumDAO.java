package tw.com.ispan.ted.dao;

import tw.com.ispan.ted.domain.OrdersumBean;

import java.util.Date;
import java.util.List;

public interface OrdersumDAO {
    List<OrdersumBean> select(Date fromDate, Date endDate);
}
