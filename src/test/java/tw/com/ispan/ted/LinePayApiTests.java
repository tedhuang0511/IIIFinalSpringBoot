package tw.com.ispan.ted;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.com.ispan.ted.service.LinePayService;

@SpringBootTest
public class LinePayApiTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    LinePayService linePayService;

    @Test
    void testPaymentRequest(){
        String paymentURI = linePayService.paymentRequest(11,"","產品名","20220618097");
        System.out.println(paymentURI);
    }

    @Test
    void testPaymentConfirm(){
        System.out.println(linePayService.paymentConfirm(11,"2022061800716459710"));
    }
}
