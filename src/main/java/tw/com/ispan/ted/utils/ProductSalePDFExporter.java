package tw.com.ispan.ted.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductSalePDFExporter {
    private List<Object[]> productsaleList;
    private Date sDate;
    private Date endDate;
    private SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ProductSalePDFExporter(List<Object[]> productsaleList, Date sDate, Date endDate) {
        this.productsaleList = productsaleList;
        this.sDate = sDate;
        this.endDate = endDate;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(BaseColor.BLACK);

        cell.setPhrase(new Phrase("Product Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sale Quantity", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) throws DocumentException, IOException {
        Long totalQty = 0L;

        BaseFont chinese = BaseFont.createFont("static/fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font FontChinese = new Font(chinese, 12, Font.NORMAL);
        for (var sbean : productsaleList) {
            totalQty = totalQty + (Long)sbean[1];
            PdfPCell cell = new PdfPCell();   //建立一個儲存格
            //透過 Paragraph 物件增加元素及指定編碼, 也可以直接存入字串
            cell.addElement(new Paragraph(String.valueOf(sbean[0]), FontChinese));
            table.addCell(cell);
            table.addCell(String.valueOf(sbean[1]));
        }
        table.addCell("");
        table.addCell("Total: "+ totalQty);
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException, ParseException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(20);
        font.setColor(BaseColor.BLUE);

        Image img = Image.getInstance("https://s3.ap-northeast-1.amazonaws.com/tedawsbucket20220530/javaproject/2022_06_24_15_31_49_647.jpg");
        img.scalePercent(25);
        document.add(img);

        Paragraph p = new Paragraph("Product Sales Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        var p2 =  new Paragraph("From:    "+sFormat.format(sDate));
        var p3 = new Paragraph("    To:    "+sFormat.format(endDate));
        document.add(p);
        document.add(p2);
        document.add(p3);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.5f, 1.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
