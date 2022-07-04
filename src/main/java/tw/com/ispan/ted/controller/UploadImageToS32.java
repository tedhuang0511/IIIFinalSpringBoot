package tw.com.ispan.ted.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import tw.com.ispan.ted.domain.ProductBean;
import tw.com.ispan.ted.service.ProductService;
import tw.com.ispan.ted.utils.AwsS3Util;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@ApiIgnore
@RequestMapping("/api")
public class UploadImageToS32{
    @Autowired
    ProductService productService;
    @Autowired
    AwsS3Util awsS3Util;

    @PostMapping(value = "/UploadImageToS32",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("pdImageColumn") String columnName,
                            @RequestParam("imgPdId")  String productId,
                            @RequestParam("files") MultipartFile file) throws IOException { //如果一次要上傳多個要改這邊 MultipartFile[] uploadfiles
        var inputStream = file.getInputStream();
        String filename = createNewFilename();
        var objMetadata = new ObjectMetadata();
        objMetadata.setContentType("image/jpg");
        String s3Url = awsS3Util.uploadToS3(filename,inputStream,objMetadata);
        String imgUrl = s3Url.substring(0,s3Url.indexOf("?"));
        //---分隔線  以上處理file上傳s3  以下處理url存到database---
        ProductBean bean = new ProductBean();
        bean.setProductId(Integer.parseInt(productId));
        var res = productService.select(bean);
        if(!res.isEmpty()){
            var temp = res.get(0);
            if("product_img1".equals(columnName)){
                temp.setProductImg1(imgUrl);
            } else if ("product_img2".equals(columnName)) {
                temp.setProductImg2(imgUrl);
            } else if ("product_img3".equals(columnName)) {
                temp.setProductImg3(imgUrl);
            } else{
                temp.setProductImg4(imgUrl);
            }
            var res2 = productService.update(temp);
            return ResponseEntity.ok(res2);
        }
        return ResponseEntity.notFound().build();
    }
    private String createNewFilename() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        String fname = sd.format(new Date()) + ".jpg";
        return fname;
    }

}
