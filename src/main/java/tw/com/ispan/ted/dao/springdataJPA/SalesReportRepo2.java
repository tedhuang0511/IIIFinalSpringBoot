package tw.com.ispan.ted.dao.springdataJPA;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.ted.domain.OrdersumBean;
import tw.com.ispan.ted.domain.Ordersumv2Bean;

import java.util.Date;
import java.util.List;

@Repository
public interface SalesReportRepo2 extends JpaRepository<Ordersumv2Bean, Integer> {
    List<Ordersumv2Bean> findByMemberIdOrderByOrderIdDesc (Integer memberId);
}
