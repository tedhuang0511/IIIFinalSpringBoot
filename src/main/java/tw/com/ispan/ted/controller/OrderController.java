package tw.com.ispan.ted.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.ted.domain.*;
import tw.com.ispan.ted.service.*;
import tw.com.ispan.ted.utils.JavaMail;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController{
    private SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderReportService orderReportService;
    @Autowired
    private MembersService membersService;
    @Autowired
    private ProductService productService;
    @Autowired
    LinePayService linePayService;

    private Integer memberId;

    @PostMapping("/orders")
    public ResponseEntity<?> selectAll(@RequestBody MemberOrderBean bean){
        System.out.println("xxxxx");
        System.out.println(bean);
        System.out.println("xxxxx");
        if (Objects.equals(bean.getOrderId(), "")){
            bean.setOrderId("XX");
        }
        if (bean.getMemberId()==0){
            bean.setMemberId(0);
        }
        if (Objects.equals(bean.getStatus(), "")){
            bean.setStatus("00");
        }
        List<Object> result = orderService.select(bean);
        if(result.size()==2){ //如果是有order+order detail
            MemberOrderBean result1 = (MemberOrderBean) result.get(0);
            System.out.println("XXXXXXXXXXXX  " + result1.getCreateDate() + "  XXXXXXXXXXXXX");
            if(result1!=null){
                //重構字串,把memberorderbean JSON裡面多加一個key(訂單明細),value放orderdetailbean的陣列裡面包json物件
                var str = "[{" +
                        "\"訂單編號\" : " + '\"' + result1.getOrderId() + '\"' +
                        ", \"會員編號\" : " + '\"'+ result1.getMemberId() + '\"' +
                        ", \"付款方式\" : " + '\"'+ result1.getPayMethod() + '\"' +
                        ", \"狀態\" : " + '\"'+ result1.getStatus() + '\"' +
                        ", \"訂單建立日期\" : " + '\"'+ result1.getCreateDate().toString() + '\"' +
                        ", \"出貨日期\" : " + '\"'+ result1.getDeliveredDate() + '\"' +
                        ", \"到貨超商\" : " + '\"'+ result1.getDeliverCvs() + '\"' +
                        ", \"宅配地址\" : " + '\"'+ result1.getDeliverAddr() + '\"' +
                        ", \"已交付日期\" : " + '\"'+ result1.getReceivedDate() + '\"' +
                        ", \"訂單明細\" : " + result.get(1) +
                        "}]";
                return ResponseEntity.ok(str);
            }else{
                return ResponseEntity.notFound().build();
            }
        }else{//只有orders
            List<MemberOrderBean> result1 = (List<MemberOrderBean>) result.get(0);
            if(result1!=null){
                var arr = new ArrayList<>();
                for(var mobean: result1){
                    arr.add(mobean.toString());
                }
                return ResponseEntity.ok(arr.toString());
            }
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/orders/{action}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("action") String action,
                                               @RequestBody MemberOrderBean bean,
                                               HttpSession session){
        bean.setUpdateUser((String) session.getAttribute("login"));
        bean.setUpdateDate(new Date());
        if("Deliver".equals(action)){
            System.out.println("come in update statement at OrderController");
            Boolean result = orderService.deliver(bean);
            if (!result) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok("更新訂單成功(已出貨)");
            }
        }else if("Receive".equals(action)){
            boolean result = orderService.receive(bean);
            if(!result) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok("更新訂單成功(買家已取貨)");
            }
        }else if("CancelOrder".equals(action)){
            System.out.println("CancelOrder in orderController");
            boolean result = orderService.cancelOrder(bean);
            if(!result) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok("訂單取消");
            }
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/orders/linepay/request")
    public ResponseEntity<?> linepayRequest(@RequestBody String body, HttpSession session){
        var temp = (MembersBean) session.getAttribute("user");
        memberId = temp.getMemberId();
        System.out.println("memberID="+memberId);
        JSONObject jobj = new JSONObject(body);
        String paymentURI = linePayService.paymentRequest((Integer) jobj.get("amount"),"",(String)jobj.get("productName"),(String)jobj.get("orderId"));
        if("error9998".equals(paymentURI)){
            ResponseEntity.notFound().build();
        }
        session.setAttribute("amount", jobj.get("amount"));
        return ResponseEntity.ok(paymentURI);
    }
    @GetMapping("/orders/linepay/{transId}")
    public ResponseEntity<?> linpayConfirm(@PathVariable("transId") String transId
            ,HttpSession session){
        System.out.println(transId);
        String result = linePayService.paymentConfirm((Integer) session.getAttribute("amount"),transId);
        if("ok".equals(result)){
            return ResponseEntity.ok("ok");//前端done繼續呼叫createOrder api
        }else {
            return ResponseEntity.ok(result);//如果拿到的body不等於ok 前端alert付款失敗
        }
    }

    @PostMapping("/orders/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody MemberOrderBean bean,
                                         HttpSession session) {
        System.out.println("createOrder api requestBody="+bean);
        var tempMbean = (MembersBean) session.getAttribute("user");
        memberId = tempMbean.getMemberId();
        System.out.println("memberid from createOrder api="+memberId);
        bean.setMemberId(memberId);  //後端自己從session抓到memberID放進去
        bean.setCreateDate(new Date());
        bean.setUpdateDate(new Date());
        bean.setSeqno(0); //seqno是 PK+AI 永遠不會讓使用者自行決定所以都給0, DAO發現是0之後就會自己去生成seqno
        bean.setCreateUser((String) session.getAttribute("login"));
        bean.setUpdateUser((String) session.getAttribute("login"));
        if (bean.getPayMethod().equals("貨到付款")) {
            bean.setStatus("01");
        } else {
            bean.setStatus("02");
        }
        int result;
        try {
            orderService.insert(bean);
            result = 0;
            //處理購物車清單填資料到orderDetailtable
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
            //這個Map是要給訂單發送email用的
            Map<ProductBean,OrderDetailBean> forOrderMail = new HashMap<>();
            for (var v : cart.keySet()) {
                ProductBean temp = new ProductBean();
                temp.setProductId(v);
                ProductBean pdbean = productService.select(temp).get(0);
                OrderDetailBean odbean = new OrderDetailBean();
                odbean.setOrderId(bean.getOrderId());
                odbean.setProductId(v);
                odbean.setQuantity(cart.get(v));//該產品的數量
                odbean.setUnitPrice(pdbean.getProductPrice());//取得產品的價格
                orderService.insert(odbean);
                result++;
                forOrderMail.put(pdbean,odbean);
            }
            sendOrderMail(forOrderMail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("error82");
        }
        //把session購物車清空
        session.removeAttribute("cart");
        return ResponseEntity.ok("訂單新增完成 ! " + "  明細增加 : " + result + " 筆");
    }
    //singleTotal,productName,productImg,qty
    private void sendOrderMail(Map map){
        //發送訂單明細到member的email//
        MembersBean mbean = new MembersBean();
        mbean.setMemberId(memberId); //先寫死 之後從session抓memberId
        var member = membersService.select(mbean).get(0);
        JavaMail javaMail = new JavaMail(member.getMemberEmail(),member.getMemberLastname()+" "+member.getMemberFirstname());
        String res = javaMail.sendMail(javaMail.createText(map));
        System.out.println(res);
    }

    @GetMapping("/orders/{memberId}")
    public ResponseEntity<?> getMemberOrderList(@PathVariable("memberId") Integer id){
        List<Ordersumv2Bean> result = orderReportService.memberOrderDetail(id);
        if(result!=null && !result.isEmpty()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }
}
