package tw.com.ispan.ted.dao;

import tw.com.ispan.ted.domain.ProductBean;

import java.util.Date;
import java.util.List;

public interface ProductDAO {

	ProductBean select(Integer id);

	List<ProductBean> select();

	List<ProductBean> select(String pdname, String pdtype);

	ProductBean insert(ProductBean bean) throws Exception;

	ProductBean update(String name, Integer price,
					   String desc, String catalog, Integer id , Date updateDate, String updateUser);

	ProductBean updateImg(String imgIndex, Integer id);

	boolean delete(Integer id);

}