package tw.com.ispan.ted.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tw.com.ispan.ted.domain.OrdersumBean;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OrderSalesExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<OrdersumBean> saleList;

    public OrderSalesExcelExporter(List<OrdersumBean> saleList) {
        this.saleList = saleList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Sales");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "訂單編號", style);
        createCell(row, 1, "會員編號", style);
        createCell(row, 2, "訂單狀態", style);
        createCell(row, 3, "付款方式", style);
        createCell(row, 4, "建立日期", style);
        createCell(row, 5, "訂單總額", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if(value instanceof String){
            cell.setCellValue((String) value);
        }else if(value instanceof Timestamp){
            cell.setCellValue(value.toString());
        }else if(value instanceof BigDecimal){
            cell.setCellValue(value.toString()); //bigDecimal不能直接轉String 要用toString
        } else {
            cell.setCellValue((Long) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        int orderTotalAmount = 0;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (var orderSumBean : saleList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            orderTotalAmount += Integer.parseInt(orderSumBean.getOrderTotalAmount().toString());
            createCell(row, columnCount++, orderSumBean.getOrderId(), style);
            createCell(row, columnCount++, orderSumBean.getMemberId(), style);
            createCell(row, columnCount++, orderSumBean.getStatus(), style);
            createCell(row, columnCount++, orderSumBean.getPayMethod(), style);
            createCell(row, columnCount++, orderSumBean.getCreateDate(), style);
            createCell(row, columnCount++, orderSumBean.getOrderTotalAmount(), style);
        }

        //最後一行做金額統計
        CellStyle style2 = workbook.createCellStyle();
        XSSFFont font2 = workbook.createFont();
        font2.setFontHeight(15);
        font2.setBold(true);
        style2.setFont(font2);
        Row row = sheet.createRow(rowCount);
        createCell(row, 5,orderTotalAmount,style2);
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}