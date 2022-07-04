package tw.com.ispan.ted.service;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.xml.transform.Result;
import java.net.URI;

@Service
public class LinePayService {
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
    }

    public String paymentRequest(Integer amount, String img, String productname, String orderId) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("amount", amount);
        jsonObj.put("productImageUrl", img);
        jsonObj.put("confirmUrl", "http://localhost:8081/checkout/linepay");
        jsonObj.put("productName", productname);
        jsonObj.put("orderId", orderId);
        jsonObj.put("currency", "TWD");

        URI uri = URI.create("https://sandbox-api-pay.line.me/v2/payments/request");
        RequestEntity<String> request = RequestEntity
                .post(uri)
                .header("X-LINE-ChannelId", "1657198233")
                .header("X-LINE-ChannelSecret", "65e7b255439f40f09ddfcf567e1dd996")
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObj.toString());

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        JSONObject result1 = new JSONObject(response.getBody());
        if ("0000".equals(result1.get("returnCode").toString())) {
            System.out.println("body=" + response.getBody());
            var result2 = new JSONObject(result1.get("info").toString());
            var result3 = new JSONObject(result2.get("paymentUrl").toString());
            var paymnetURL = result3.get("web");
            return paymnetURL.toString();
        } else {
            //return result1.get("returnMessage").toString();
            return "error9998";
        }
    }

    public String paymentConfirm(Integer amount, String transID) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("amount", amount);
        jsonObj.put("currency", "TWD");

        URI uri = URI.create("https://sandbox-api-pay.line.me/v2/payments/" + transID + "/confirm");
        RequestEntity<String> request = RequestEntity
                .post(uri)
                .header("X-LINE-ChannelId", "1657198233")
                .header("X-LINE-ChannelSecret", "65e7b255439f40f09ddfcf567e1dd996")
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObj.toString());

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        JSONObject result1 = new JSONObject(response.getBody());
        if ("0000".equals(result1.get("returnCode").toString())) {
            System.out.println("body=" + response.getBody());
            return "ok";
        } else {
            return result1.get("returnMessage").toString();
        }

    }
}
