package tw.com.ispan.ted.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import tw.com.ispan.ted.domain.OrdersumBean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderSalesPDFExporter {
    private List<OrdersumBean> saleList;
    private Date sDate;
    private Date endDate;
    private SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public OrderSalesPDFExporter(List<OrdersumBean> saleList, Date sDate, Date endDate) {
        this.saleList = saleList;
        this.sDate = sDate;
        this.endDate = endDate;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(BaseColor.WHITE);

        cell.setPhrase(new Phrase("Order ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Member ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Pay methods", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Create Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Amount", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) throws DocumentException, IOException {
        Integer totalAmount = 0;
        BaseFont chinese = BaseFont.createFont("static/fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font FontChinese = new Font(chinese, 12, Font.NORMAL);
        for (var orderSumBean : saleList) {
            totalAmount = totalAmount + Integer.parseInt(orderSumBean.getOrderTotalAmount().toString());
            table.addCell(orderSumBean.getOrderId());
            table.addCell(String.valueOf(orderSumBean.getMemberId()));

            PdfPCell cell = new PdfPCell();
            cell.addElement(new Paragraph(orderSumBean.getStatus(), FontChinese));
            table.addCell(cell);

            table.addCell(orderSumBean.getPayMethod());
            table.addCell(sFormat.format(orderSumBean.getCreateDate()));
            table.addCell(String.valueOf(orderSumBean.getOrderTotalAmount()));
        }
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Total= "+ totalAmount);
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException, ParseException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(20);
        font.setColor(BaseColor.BLACK);

        Image img = Image.getInstance("https://s3.ap-northeast-1.amazonaws.com/tedawsbucket20220530/javaproject/2022_06_24_15_31_49_647.jpg");
        img.scalePercent(50);
        document.add(img);

        Paragraph p = new Paragraph("Sales Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        var p2 =  new Paragraph("From:    "+sFormat.format(sDate));
        var p3 = new Paragraph("    To:    "+sFormat.format(endDate));
        document.add(p);
        document.add(p2);
        document.add(p3);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.6f, 1.5f,1.5f, 1.5f,2.6f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
