package tw.com.ispan.ted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.ted.dao.OrdersumDAO;
import tw.com.ispan.ted.dao.springdataJPA.SalesReportRepo;
import tw.com.ispan.ted.dao.springdataJPA.SalesReportRepo2;
import tw.com.ispan.ted.domain.OrdersumBean;
import tw.com.ispan.ted.domain.Ordersumv2Bean;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class OrderReportService {
	@Autowired
	private OrdersumDAO ordersumDAO;
	@Autowired
	private SalesReportRepo salesReportRepo;
	@Autowired
	private SalesReportRepo2 salesReportRepo2;
	
	public List<OrdersumBean> select(Date fromDate, Date endDate){
		List<OrdersumBean> result = null;
		if(fromDate!=null && endDate!=null) {
			List<OrdersumBean> temp = ordersumDAO.select(fromDate,endDate);
			if(temp!=null&&temp.size()!=0) {
				result = temp;
				return result;
			}
		}else {
//			result = productsaleDAOHibernate.select();
		}	
		return result;
	};

	public Object[] pageableSelect(Date sDate, Date endDate, int page) throws ParseException {
		Pageable firstPageWithTwoElements = PageRequest.of(page, 6);
		var result1 =
				salesReportRepo
						.findByCreateDateBetween(sDate,endDate,firstPageWithTwoElements);
		if(!result1.isEmpty()) {
			System.out.println("Size="+result1.getSize());
			System.out.println("Number="+result1.getNumber());
			System.out.println("NumberOfElements="+result1.getNumberOfElements());
			System.out.println("TotalPages="+result1.getTotalPages());
			System.out.println("TotalElements="+result1.getTotalElements());
			Map pageInfo = new HashMap();
			pageInfo.put("currentPage",result1.getNumber());
			pageInfo.put("totalPage",result1.getTotalPages());
			List<OrdersumBean> beans = result1.getContent();
			for(OrdersumBean bean : beans) {
				System.out.println("Page="+bean);
			}
			Object[] data = {pageInfo,beans};
			return data;
		}
		return null;
	}

	public List<Ordersumv2Bean> memberOrderDetail(Integer memberId){
		var result = salesReportRepo2.findByMemberIdOrderByOrderIdDesc(memberId);
		if(result!=null && !result.isEmpty()){
			return result;
		}
		return null;
	}
}
