package tw.com.ispan.ted.dao.springdataJPA;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.ted.domain.OrdersumBean;
import tw.com.ispan.ted.domain.UserBean;

import java.util.Date;
import java.util.List;

@Repository
public interface SalesReportRepo extends JpaRepository<OrdersumBean, Integer> {
    Page<OrdersumBean> findByCreateDateBetween (Date date1, Date date2, Pageable pageable);
}
