package tw.com.ispan.ted.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.ted.dao.springdataJPA.SalesReportRepo;
import tw.com.ispan.ted.dao.springdataJPA.SalesReportRepo2;
import tw.com.ispan.ted.domain.OrdersumBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class SaleReportRepositoryTests {
	@Test
	void contextLoads() {
	}


	@Autowired
	private SalesReportRepo salesReportRepo;
	@Test
	@Transactional
	void testSelect() throws ParseException {
		String sDate1="2022-06-01 00:00:00";
		String sDate2="2022-06-30 23:59:59";
		Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate1);
		Date date2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate2);
		Pageable firstPageWithTwoElements = PageRequest.of(1, 6);
		var result1 =
				salesReportRepo
						.findByCreateDateBetween(date1,date2,firstPageWithTwoElements);
		if(!result1.isEmpty()) {
			System.out.println("Size="+result1.getSize());
			System.out.println("Number="+result1.getNumber());
			System.out.println("NumberOfElements="+result1.getNumberOfElements());
			System.out.println("TotalPages="+result1.getTotalPages());
			System.out.println("TotalElements="+result1.getTotalElements());

			List<OrdersumBean> beans = result1.getContent();
			for(OrdersumBean bean : beans) {
				System.out.println("Page="+bean);
			}
		}
	}
//	@Autowired
//	private SalesReportRepo2 salesReportRepo2;

//	@Test
//	@Transactional
//	void test22(){
//		var temp = salesReportRepo2.findByMemberId(2);
//		for(var v : temp){
//			System.out.println(v);
//		}
//	}

}
