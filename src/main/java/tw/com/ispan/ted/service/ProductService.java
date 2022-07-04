package tw.com.ispan.ted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.ted.dao.ProductDAO;
import tw.com.ispan.ted.domain.ProductBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductDAO productDao;

    @Transactional(readOnly = true)
    public List<ProductBean> select(ProductBean bean) {
        System.out.println("in PS select method");
        List<ProductBean> result = null;
        try{
            if(bean!=null && bean.getProductId()!=null && !bean.getProductId().equals(0)) { //如果傳進來的bean不是空 && getId不是空 && id != 0
                ProductBean temp = productDao.select(bean.getProductId());  //呼叫DAO有id參數的select方法
                if(temp!=null) {
                    result = new ArrayList<>();
                    result.add(temp);
                    return result;
                }
            } else if(bean!=null && (bean.getProductCatalog()!=null || bean.getProductName()!=null)){
                System.out.println("XQXQQXQ");
                result = productDao.select(bean.getProductName(),bean.getProductCatalog());
                return result;
            } else{
                System.out.println("55555555gf");
                result = productDao.select();
                return result;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public ProductBean insert(ProductBean bean) throws Exception {
        ProductBean result = null;
        if(bean!=null && bean.getProductId()==null) {
            result = productDao.insert(bean);
        }
        return result;
    }

    public ProductBean update(ProductBean bean) {
        ProductBean result = null;
        if(bean!=null && bean.getProductId()!=null) {
            result = productDao.update(bean.getProductName(), bean.getProductPrice(),
                    bean.getProductDesc(), bean.getProductCatalog(), bean.getProductId(), bean.getUpdateDate(), bean.getUpdateUser());
        }
        return result;
    }

    public ProductBean deleteImg(ProductBean bean, String index) {
        ProductBean result = null;
        if(bean!=null && bean.getProductId()!=null) {
            result = productDao.updateImg(index, bean.getProductId());
        }
        return result;
    }

    public boolean delete(ProductBean bean) {
        boolean result = false;
        if(bean!=null && bean.getProductId()!=null) {
            result = productDao.delete(bean.getProductId());
        }
        return result;
    }

}
