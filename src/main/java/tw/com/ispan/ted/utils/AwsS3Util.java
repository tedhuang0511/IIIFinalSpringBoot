package tw.com.ispan.ted.utils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.net.URL;

@Service
public class AwsS3Util {
    private AmazonS3 s3Client;
    @Value("${awsS3.access}")
    private String accessKey;
    @Value("${awsS3.secret}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        Regions regions = Regions.AP_NORTHEAST_1; //Region
        AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey); //憑證
        this.s3Client = AmazonS3ClientBuilder.standard() //建立連線
                .withRegion(regions)
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
    }
    String bucketName = "tedawsbucket20220530"; // 【你 bucket 的名字】 # 首先需要保證 s3 上已經存在該儲存桶
    public String uploadToS3(String remoteFileName, InputStream inputStream, ObjectMetadata objectMetadata) {
        try {
            String bucketPath = bucketName + "/javaproject";
            s3Client.putObject(new PutObjectRequest(bucketPath, remoteFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketPath, remoteFileName);
            URL url = s3Client.generatePresignedUrl(urlRequest);
            System.out.println("upload success");
            return url.toString();
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
            ase.toString();
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
            ace.toString();
        }
        return null;
    }
//    public static void main(String[] args) throws IOException {
//        File uploadFile = new File("D:\\Program Files\\IIIFinalServlet\\src\\main\\webapp\\images\\fist2.png");
//        System.out.println(uploadFile.length());
//        String remoteName = "123.jpg";
//        String s3url = uploadToS3(uploadFile, remoteName);
//        System.out.println(s3url.substring(0,s3url.indexOf("?")));
//    }
}

