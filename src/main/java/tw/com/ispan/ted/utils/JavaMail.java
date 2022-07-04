package tw.com.ispan.ted.utils;

import tw.com.ispan.ted.domain.OrderDetailBean;
import tw.com.ispan.ted.domain.ProductBean;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
public class JavaMail {
    private final String username;
    private final String password;
    private final Properties props;
    private final String memberEmail;
    private final String title;
    private final String memberName;

    public JavaMail(String memberEmail, String memberName) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.title = "Java webapp 網路購物訂單確認信";
        String host = "smtp.gmail.com";
        int port = 587;
        username = "sbbty218@gmail.com";
        password = "xrqonlxqjfhweukl";

        props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", port);
    }

    public String sendMail(String text) {
        String to = memberEmail;
        try {
            Message message = createMessage(to, title , text);
            Transport.send(message);
            return "郵件傳送成功";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendPWMail(String text) {
        String to = memberEmail;
        try {
            String content0 = "親愛的"+memberName+"您好，";
            String content1 = "已幫您重設密碼為:";
            Message message = createMessage(to, "您的CMA購物網密碼已經被重置" ,content0+content1+text);
            Transport.send(message);
            return "郵件傳送成功";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message createMessage(String to, String subject, String text) throws MessagingException {
        Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                }
        );
        Message message = new MimeMessage(session);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setContent(multipart(text));
        return message;
    }

    private Multipart multipart(String text) throws MessagingException {
        Multipart mp = new MimeMultipart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(text, "text/html;charset=UTF-8");
        mp.addBodyPart(htmlPart);
        return mp;
    }

    public String createText(Map map) {  //ordercontroller會傳入Map裡面裝K:productBean V:orderDetailBean
        String str = "";
        int totalPrice = 0;
        for(Object temp : map.keySet()){
            OrderDetailBean odbean = (OrderDetailBean) map.get(temp);
            totalPrice = totalPrice + odbean.getUnitPrice()*odbean.getQuantity();
            ProductBean pdbean = (ProductBean) temp;
            str = str + "<tr>" +
                    "<td style=\"padding: 16px; font-size: 18px;\">" + pdbean.getProductName() + "\uD83D\uDD25" + "</td>\n" +
                    "<td style=\"padding: 16px;\">" + "<img src=\"" + pdbean.getProductImg1() + "\" width=\"100px\">" + "</td>\n" +
                    "<td style=\"padding: 16px; font-size: 16px;\">" + odbean.getQuantity() + "</td>\n" +
                    "</tr>";
        }
        System.out.println(str);
        String text1 = "<body>\n" +
                "<h3>親愛的  " +"<a style=\"font-size: 31px; color: #31b0d5\">"+memberName+"</a>"+"  您好 \uD83D\uDE4C\uD83D\uDE4C</h3>" +
                "<h1>\uD83D\uDD7A我們已經收到您的訂單，將會盡快安排為您出貨\uD83D\uDD7A</h1>\n" +
                "<h3>以下是您的訂購明細 ：</h3>\n" +
                "\n" +
                "<table style=\"border:3px #00D6D6 dashed; width: 100%;\" cellpadding=\"10\" border='1'>" +
                "  <tr>\n" +
                "    <th style=\"padding: 16px; text-align: left; width:35%;\">產品名稱</th>\n" +
                "    <th style=\"padding: 16px; text-align: left; width:30%;\">產品照片</th>\n" +
                "    <th style=\"padding: 16px; text-align: left; width:35%;\">訂購數量</th>\n" +
                "  </tr>\n"
                + str +
                "  <tr>\n" +
                "    <td></td>\n" +
                "    <td></td>\n" +
                "    <td style=\"padding: 16px;\">訂單總計:<b>"+ totalPrice +"</b></td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "</body>";
        return text1;
    }

//    public static void main(String[] args) {
//        JavaMail javaMail = new JavaMail("sbbty218@gmail.com","Ted Huang");
//        String res = javaMail.sendMail(javaMail.createText("[\n" +
//                "    {\n" +
//                "        \"productId\": 52,\n" +
//                "        \"qty\": 1,\n" +
//                "        \"productImg\": \"https://s3.ap-northeast-1.amazonaws.com/tedawsbucket20220530/javaproject/2022_06_02_21_50_52_422.jpg\",\n" +
//                "        \"singleTotal\": 18899,\n" +
//                "        \"productName\": \"Samsung S22 Ultra\",\n" +
//                "        \"productPrice\": 18899\n" +
//                "    }\n" +
//                "]"));
//        System.out.println(res);
//    }
}
