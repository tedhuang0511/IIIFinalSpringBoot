package tw.com.ispan.ted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.ted.dao.MemberOrderDAO;
import tw.com.ispan.ted.dao.OrderDetailDAO;
import tw.com.ispan.ted.domain.MemberOrderBean;
import tw.com.ispan.ted.domain.OrderDetailBean;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private MemberOrderDAO memberOrderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Transactional(readOnly = true)
    public List<Object> select(MemberOrderBean bean) {
        System.out.println("in order select method");
        List<Object> result1 = new ArrayList<>();
        if(bean!=null && bean.getOrderId()!=null && !bean.getOrderId().equals("XX")) { //如果傳進來的bean不是空 && getId不是空 && id != "XX"
            MemberOrderBean temp = memberOrderDAO.select(bean.getOrderId()); //根據id找出唯一訂單
            List<OrderDetailBean> temp1 = null;
            if (temp != null) {
                result1.add(temp);//如果有找到id就把找到的bean塞進去
                temp1 = orderDetailDAO.select(bean.getOrderId()); //根據id找出可能多個明細
            }
            result1.add(temp1);
            return result1;  //回傳一個list裡面有 1.member order bean 2.這個orderbean的多個detail bean
        } else {
            //如果request沒有包含orderId,而是有member id 或 status 就會進來
            assert bean != null;
            Integer mid = bean.getMemberId();
            String status = bean.getStatus();
            System.out.println("in OS multiple select method"+":"+mid+":"+status);
            List<MemberOrderBean> temp2 = memberOrderDAO.select(mid,status); //呼叫DAO有兩個參數的select方法
            result1.add(temp2);
        }
        return result1;
    }
    public MemberOrderBean insert(MemberOrderBean bean) throws Exception {
        MemberOrderBean result = null;
        if(bean!=null && bean.getOrderId()!=null) {
            System.out.println(bean.getOrderId() + " from order service");
            result = memberOrderDAO.insert(bean);
        }
        return result;
    }

    public int insert(OrderDetailBean bean) throws Exception {
        var result = 0;
        if (bean != null && bean.getOrderId() != null) {
            System.out.println(bean.getOrderId() + " detail insert from order service!!");
            result = orderDetailDAO.insert(bean);
        }
        return result;
    }

    public Boolean deliver(MemberOrderBean bean) {
        Boolean result = null;
        if(bean!=null && bean.getOrderId()!=null) {
            result = memberOrderDAO.deliver(bean);
        }
        return result;
    }

    public Boolean receive(MemberOrderBean bean) {
        Boolean result = null;
        if(bean!=null && bean.getOrderId()!=null) {
            result = memberOrderDAO.receive(bean);
        }
        return result;
    }

    public boolean cancelOrder(MemberOrderBean bean) {
        boolean result = false;
        if(bean!=null && bean.getOrderId()!=null) {
            result = memberOrderDAO.cancelOrder(bean);
        }
        return result;
    }

}
