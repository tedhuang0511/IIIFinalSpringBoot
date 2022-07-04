package tw.com.ispan.ted;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordTest {
    @Test
    void contextLoads(){

    }

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void testEncrypt(){
        String root = encryptor.encrypt("xrqonlxqjfhweukl");
        System.out.println(root);
        //pDbzeJ03Mu959HJieLeFZVl85yPBA7W1mM9+zuwhVQw4we1nfaEIL3uJYO1tK3rK
    }

    @Test
    public void testDecrypt(){
        var aaa = encryptor.decrypt("hIXid79atnkRTPCImOErK3rPV8Sebd39+M/UAili1XrzOXQ0aqkPOuIGD6sgc5nTWxa8zZkWgy4YHusRWdoWShUyveH/xWv25YJJ3ohyxHI=");
        System.out.println(aaa);
    }
}
