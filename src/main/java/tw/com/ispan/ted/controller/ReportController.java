package tw.com.ispan.ted.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.ted.domain.OrdersumBean;
import tw.com.ispan.ted.service.OrderReportService;
import tw.com.ispan.ted.service.ProductSalesService;
import tw.com.ispan.ted.utils.OrderSalesExcelExporter;
import tw.com.ispan.ted.utils.OrderSalesPDFExporter;
import tw.com.ispan.ted.utils.ProductSalePDFExporter;
import tw.com.ispan.ted.utils.ProductSalesExcelExporter;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReportController {
    private SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private ProductSalesService productSalesService;
    @Autowired
    private OrderReportService orderReportService;

    @PostMapping("/reports/{type}")
    public ResponseEntity<?> getReports(@PathVariable("type") String type,
                                        @RequestBody String body) throws ParseException {
        JSONObject jobj = new JSONObject(body);
        Date sDate = sFormat.parse((String) jobj.get("startDate"));
        Date endDate = sFormat.parse((String) jobj.get("endDate"));

        if("order".equals(type)){
            List<OrdersumBean> res = orderReportService.select(sDate,endDate);
            if(res!=null && res.size()!=0){
                return ResponseEntity.ok(res);
            }else{
                return ResponseEntity.notFound().build();
            }
        }else if("product".equals(type)){
            JSONArray jarr = new JSONArray();
            List<Object[]> res = productSalesService.select(sDate, endDate);
            if(res!=null && res.size()!=0){
                for (var bean : res) {
                    Map<Object,Object> map = new HashMap<>();
                    map.put(bean[0],bean[1]);
                    jarr.put(new JSONObject(map));
                }
                return ResponseEntity.ok(jarr.toString());
            }else{
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/reports2/{type}")
    public ResponseEntity<?> getReports2(@PathVariable("type") String type,
                                        @RequestBody String body) throws ParseException {
        JSONObject jobj = new JSONObject(body);
        Date sDate = sFormat.parse((String) jobj.get("startDate"));
        Date endDate = sFormat.parse((String) jobj.get("endDate"));
        int page = jobj.getInt("page");

        if("order".equals(type)){
            Object[] obj = orderReportService.pageableSelect(sDate,endDate,page);
            Map pageInfo = (Map) obj[0];
            int totalPage = (int) pageInfo.get("totalPage");
            List<OrdersumBean> res = (List<OrdersumBean>) obj[1];
            if(res!=null && res.size()!=0){
                JSONObject jobj2 = new JSONObject();
                jobj2.put("returnCode", "0000");
                jobj2.put("totalPage", totalPage);
                jobj2.put("data", res);
                return ResponseEntity.ok(jobj2.toString());
            }else{
                return ResponseEntity.notFound().build();
            }
        }else if("product".equals(type)){
            JSONArray jarr = new JSONArray();
            List<Object[]> res = productSalesService.select(sDate, endDate);
            if(res!=null && res.size()!=0){
                for (var bean : res) {
                    Map<Object,Object> map = new HashMap<>();
                    map.put(bean[0],bean[1]);
                    jarr.put(new JSONObject(map));
                }
                return ResponseEntity.ok(jarr.toString());
            }else{
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    //export sales report as XLSX
    @GetMapping("/reports/{type}/export/{sDate}/{eDate}")
    public void exportToExcel(HttpServletResponse response,
                              @PathVariable("type") String type,
                              @PathVariable("sDate") String sDate,
                              @PathVariable("eDate") String eDate) throws Exception {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=name.xlsx");

        if("product".equals(type)){
            List<Object[]> listProductSales = productSalesService.select(sFormat.parse(sDate), sFormat.parse(eDate));
            ProductSalesExcelExporter productSalesExcelExporter = new ProductSalesExcelExporter(listProductSales);
            productSalesExcelExporter.export(response);
        } else if ("order".equals(type)) {
            List<OrdersumBean> res = orderReportService.select(sFormat.parse(sDate), sFormat.parse(eDate));
            OrderSalesExcelExporter orderSalesExcelExporter = new OrderSalesExcelExporter(res);
            orderSalesExcelExporter.export(response);
        }
    }

    //export sales report as PDF
    @GetMapping("/reports/pdf/{type}/export/{sDate}/{eDate}")
    public void exportToPDF(HttpServletResponse response,
                              @PathVariable("type") String type,
                              @PathVariable("sDate") String sDate,
                              @PathVariable("eDate") String eDate) throws Exception {
        response.setContentType("application/pdf;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");

        if("product".equals(type)){
            List<Object[]> listProductSales = productSalesService.select(sFormat.parse(sDate), sFormat.parse(eDate));
            ProductSalePDFExporter productSalePdfExporter = new ProductSalePDFExporter(listProductSales,sFormat.parse(sDate), sFormat.parse(eDate));
            productSalePdfExporter.export(response);

        } else if ("order".equals(type)) {
            List<OrdersumBean> res = orderReportService.select(sFormat.parse(sDate), sFormat.parse(eDate));
            OrderSalesPDFExporter orderSalesPDFExporter = new OrderSalesPDFExporter(res,sFormat.parse(sDate),sFormat.parse(eDate));
            orderSalesPDFExporter.export(response);
        }
    }
}
