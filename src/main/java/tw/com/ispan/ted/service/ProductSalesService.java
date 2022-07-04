package tw.com.ispan.ted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.ted.dao.ProductsaleDAO;

import java.util.Date;
import java.util.List;

@Component
@Transactional
public class ProductSalesService {
	@Autowired
	private ProductsaleDAO productsaleDAO;
	
	public List<Object[]> select(Date fromDate, Date endDate){
		List<Object[]> result = null;
		if(fromDate!=null && endDate!=null) {
			List<Object[]> temp = productsaleDAO.select(fromDate,endDate);
			if(temp!=null) {
				result = temp;
				return result;
			}
		}else {
//			result = productsaleDAOHibernate.select();
		}	
		return result;
	};

}
