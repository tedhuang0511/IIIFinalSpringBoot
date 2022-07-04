package tw.com.ispan.ted.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.ted.domain.MembersBean;
import tw.com.ispan.ted.domain.ProductBean;
import tw.com.ispan.ted.service.ProductService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController{
    @Autowired
    private ProductService productService;
    private Integer totalQtyInCart;
    //計算目前購物車總共有多少數量
    private Integer totalQTYinCart(HashMap<Integer, Integer> map) {
        int totalQtyInCart = 0;
        for (int id : map.keySet()) {
            totalQtyInCart += map.get(id); //算出目前所有產品的數量加總
        }
        return totalQtyInCart;
    }

    @GetMapping("/cart/checkout")
    public ResponseEntity<?> checkout(HttpSession session){
        //把購物車清單從session cart抓出來
        HashMap<Integer, Integer> result = (HashMap<Integer, Integer>) session.getAttribute("cart");
        if (result != null && result.size() != 0) {
            //把map轉成json object as response
            List list = new ArrayList<>();
            for (var k : result.keySet()) {
                ProductBean temp = new ProductBean();
                temp.setProductId(k);
                ProductBean bean = productService.select(temp).get(0);
                JSONObject jo2 = new JSONObject();
                jo2.put("productId", bean.getProductId());
                jo2.put("productName", bean.getProductName());
                jo2.put("productPrice", bean.getProductPrice());
                jo2.put("productImg", bean.getProductImg1());
                jo2.put("qty", result.get(k));
                jo2.put("singleTotal",bean.getProductPrice()*result.get(k));
                list.add(jo2);
            }
            return ResponseEntity.ok(list.toString());
        } else {
            return ResponseEntity.ok("購物車內目前沒有商品!");
        }
    }
    @GetMapping({"/cart/add/{id}/qty/{qty}","/cart/add/{id}/qty"})
    public ResponseEntity<?> addToCart(HttpSession session,
                                       @PathVariable("id") Integer id,
                                       @PathVariable(required = false) Integer qty){
        //new一個map裡面放 productId(K)跟quantity(V)
        qty = qty ==null? 1: qty;
        HashMap<Integer, Integer> cartList = new HashMap<>();
        if (session.getAttribute("cart") != null) {  //如果購物車session已存在
            HashMap<Integer, Integer> temp = (HashMap<Integer, Integer>)session.getAttribute("cart"); //把購物車session的map抓出
            if (temp.get(id) == null) {   //如果map裡面抓不到這個productId的key
                temp.put(id, qty); //map新增一個productId,qty
            } else {
                temp.put(id, temp.get(id) + qty);
            }
            session.removeAttribute("cart"); //把舊有的購物車session移除
            session.setAttribute("cart", temp); //把新的list存回購物車session
            totalQtyInCart = totalQTYinCart(temp);
            return ResponseEntity.ok("購物車新增成功" + totalQtyInCart);
        } else {
            //如果購物車session不存在 就直接new一個session把map存進去
            cartList.put(id, qty);
            session.setAttribute("cart", cartList);
            totalQtyInCart = totalQTYinCart(cartList);
            return ResponseEntity.ok("購物車新增成功" + totalQtyInCart);
        }
    }

    @GetMapping({"/cart/minus/{id}/qty/{qty}","/cart/minus/{id}/qty"})
    public ResponseEntity<?> minusToCart(HttpSession session,
                                       @PathVariable("id") Integer id,
                                       @PathVariable(required = false) Integer qty){
        //new一個map裡面放 productId(K)跟quantity(V)
        qty = qty ==null? 1: qty;
        HashMap<Integer, Integer> temp = (HashMap<Integer, Integer>) session.getAttribute("cart"); //把session存放的map拿出來
        String output = "購物車內並沒有此商品";
        if (temp.get(id) != null) {
            //如果此商品數量剩餘1 直接把產品從map移除 ; 商品數量大於1 則 -1
            if (temp.get(id) == 1) {
                temp.remove(id);
            } else {
                if (qty == 1) {
                    temp.put(id, temp.get(id) - 1);
                } else if (qty > 0 && qty < temp.get(id)) { //判斷一下前端輸入的值是否在合法範圍
                    temp.put(id, qty);
                } else {
                    return ResponseEntity.ok("error:超出範圍");
                }
            }
            output = "商品已移除，購物車內目前商品數量=";
        }
        session.removeAttribute("cart"); //把舊有的購物車session移除
        session.setAttribute("cart", temp); //把新的Map存回購物車session
        totalQtyInCart = totalQTYinCart(temp);
        return ResponseEntity.ok(output+totalQtyInCart);
    }
    @GetMapping("/cart/remove/{id}")
    public ResponseEntity<?> removeSingleProductFromCart(HttpSession session,@PathVariable("id") Integer id){
        HashMap<Integer, Integer> temp = (HashMap<Integer, Integer>) session.getAttribute("cart"); //把session存放的map拿出來
        String output = "購物車內並沒有此商品";
        if (temp.get(id) != null) {
            //如果此商品數量大於1 直接把產品從map移除
            if (temp.get(id) > 0) {
                temp.remove(id);
            } else {
                return ResponseEntity.ok("完全移除商品有誤");
            }
            output = "此商品已完全移除，購物車內目前商品數量=";
        }
        session.removeAttribute("cart"); //把舊有的購物車session移除
        session.setAttribute("cart", temp); //把新的Map存回購物車session
        totalQtyInCart = totalQTYinCart(temp);
        return ResponseEntity.ok(output + totalQtyInCart);
    }
    @GetMapping("/test/session/mid")
    public ResponseEntity<?> testmethod1(HttpSession s){
        if(s.getAttribute("user")instanceof MembersBean){
            var temp = (MembersBean) s.getAttribute("user");
            if(temp != null){
                var memberId = temp.getMemberId();
                return ResponseEntity.ok(memberId);
            }
        }else{
            Integer temp = (Integer) s.getAttribute("user");
            if (temp != null) {
//            var memberId = temp.getMemberId();
                return ResponseEntity.ok(temp);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/test/session/email")
    public ResponseEntity<?> testmethod2(HttpSession s){
        String tempEmail = (String) s.getAttribute("email");
        System.out.printf("xxx="+tempEmail);
        if(tempEmail!=null && tempEmail.length()!=0){
            System.out.println("ok");
            return ResponseEntity.ok(tempEmail);
        }
        System.out.println("not work");
        return ResponseEntity.notFound().build();
    }
}