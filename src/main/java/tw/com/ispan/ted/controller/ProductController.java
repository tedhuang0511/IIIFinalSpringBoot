package tw.com.ispan.ted.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import tw.com.ispan.ted.domain.ProductBean;
import tw.com.ispan.ted.service.ProductService;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("取得所有產品清單")
    @GetMapping("/products")
    public ResponseEntity<?> selectAll() {
        List<ProductBean> result = productService.select(null);
        if(result!=null&&!result.isEmpty()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok("no data found");
    }
    @ApiOperation("取得特定產品類別的所有產品")
    @GetMapping("/products/catalog/{catalog}")
    public ResponseEntity<?> selectAll(@PathVariable(required = false) String catalog) {
        ProductBean bean = new ProductBean();
        //這個是為了讓第二個endpoint可以順利執行避免bean.getProductName出現nullpointException
        bean.setProductName("XX");
        if (catalog != null) {
            bean.setProductCatalog(catalog);
        }
        List<ProductBean> result = productService.select(bean);
        if(result!=null&&!result.isEmpty()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok("no data found");
    }


    @ApiIgnore
    @PostMapping("/products/mutilselect")
    public List<ProductBean> selectAll2(@RequestBody ProductBean bean) {
        System.out.println(bean);
        if (Objects.equals(bean.getProductName(), "")) {
            bean.setProductName("XX");
        }
        if (Objects.equals(bean.getProductCatalog(), "")) {
            bean.setProductCatalog("XX");
        }
        List<ProductBean> result = productService.select(bean);
        return result;
    }

    @ApiOperation("取得特定產品ID的資訊")
    @GetMapping("/products/{id}")
    public ResponseEntity<?> select1(@PathVariable("id") Integer id) {
        ProductBean bean = new ProductBean();
        bean.setProductId(id);
        List<ProductBean> result = productService.select(bean);
        if (result != null && !result.isEmpty()) {
            return ResponseEntity.ok(result.get(0));
        }
        return ResponseEntity.notFound().build();
    }
    @ApiIgnore
    @PostMapping("/products")
    public ResponseEntity<?> insert(@RequestBody ProductBean bean, HttpSession session) throws Exception {
        if (bean != null) {
            System.out.println(session.getAttribute("login"));
            bean.setUpdateUser((String) session.getAttribute("login"));
            bean.setCreateUser((String) session.getAttribute("login"));
            bean.setCreateDate(new Date());
            bean.setUpdateDate(new Date());
            var res = productService.insert(bean);
            if (res != null) {
                URI uri = URI.create("/products/" + res.getProductId());
                return ResponseEntity.created(uri).body(res);
            }
        }
        return ResponseEntity.noContent().build();
    }
    @ApiIgnore
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        ProductBean bean = new ProductBean();
        bean.setProductId(id);
        boolean deleted = productService.delete(bean);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiIgnore
    @PutMapping("/products/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ProductBean bean,
                                    HttpSession session) {
        bean.setUpdateUser((String) session.getAttribute("login"));
        bean.setProductId(id);
        bean.setUpdateDate(new Date());
        ProductBean update = productService.update(bean);
        if (update != null) {
            return ResponseEntity.ok(update);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiIgnore
    @PutMapping("/products/delImg/{id}/{index}")
    public ResponseEntity<?> deleteProductImage(@PathVariable("id") Integer id,
                                                @PathVariable("index") Integer index
    ) {
        ProductBean bean = new ProductBean();
        bean.setProductId(id);
        var res = productService.deleteImg(bean, String.valueOf(index));
        if (res != null) {
            return ResponseEntity.ok("圖片刪除成功");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
