package tw.com.ispan.ted.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products", schema = "iii")
@NamedQueries(
        value = {
                @NamedQuery(
                        name="byPrice",
                        query="from tw.com.ispan.ted.domain.ProductBean where productPrice > :price"
                )
        }
)
public class ProductBean {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Basic
    @Column(name = "product_name", nullable = false, length = 30)
    private String productName;
    @Basic
    @Column(name = "product_catalog", nullable = false, length = 15)
    private String productCatalog;
    @Basic
    @Column(name = "product_price", nullable = false)
    private Integer productPrice;
    @Column(name = "product_stock")
    private Integer productStock = 0;
    @Basic
    @Column(name = "product_color", length = 5)
    private String productColor;
    @Basic
    @Column(name = "product_desc")
    private String productDesc;
    @Basic
    @Column(name = "create_user", length = 10)
    private String createUser;
    @Basic
    @Column(name = "create_date")
    private Date createDate;
    @Basic
    @Column(name = "update_user", length = 10)
    private String updateUser;
    @Basic
    @Column(name = "update_date")
    private Date updateDate;
    @Basic
    @Column(name = "product_img1")
    private String productImg1 = "https://s3.ap-northeast-1.amazonaws.com/tedawsbucket20220530/javaproject/2022_05_31_16_00_31_187.jpg";
    @Basic
    @Column(name = "product_img2")
    private String productImg2 = "https://s3.ap-northeast-1.amazonaws.com/tedawsbucket20220530/javaproject/2022_05_31_16_00_31_187.jpg";
    @Basic
    @Column(name = "product_img3")
    private String productImg3 = "https://s3.ap-northeast-1.amazonaws.com/tedawsbucket20220530/javaproject/2022_05_31_16_00_31_187.jpg";
    @Basic
    @Column(name = "product_img4")
    private String productImg4 = "https://s3.ap-northeast-1.amazonaws.com/tedawsbucket20220530/javaproject/2022_05_31_16_00_31_187.jpg";

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(String productCatalog) {
        this.productCatalog = productCatalog;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProductImg1() {
        return productImg1;
    }

    public void setProductImg1(String productImg1) {
        this.productImg1 = productImg1;
    }

    public String getProductImg2() {
        return productImg2;
    }

    public void setProductImg2(String productImg2) {
        this.productImg2 = productImg2;
    }

    public String getProductImg3() {
        return productImg3;
    }

    public void setProductImg3(String productImg3) {
        this.productImg3 = productImg3;
    }

    public String getProductImg4() {
        return productImg4;
    }

    public void setProductImg4(String productImg4) {
        this.productImg4 = productImg4;
    }

    @Override
    public String toString() {
        return "{" +
                "\"productId\" : " + '\"' + productId + '\"' +
                ", \"productName\" : " + '\"'+ productName + '\"' +
                ", \"productCatalog\" : " + '\"'+ productCatalog + '\"' +
                ", \"productPrice\" : " + '\"'+ productPrice + '\"' +
                ", \"productStock\" : " + '\"'+ productStock + '\"' +
                ", \"productDesc\" : " + '\"'+ productDesc + '\"' +
                ", \"productImg1\" : " + '\"'+ productImg1 + '\"' +
                ", \"productImg2\" : " + '\"'+ productImg2 + '\"' +
                ", \"productImg3\" : " + '\"'+ productImg3 + '\"' +
                ", \"productImg4\" : " + '\"'+ productImg4 + '\"' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductBean that = (ProductBean) o;

        if (productId != that.productId) return false;
        if (productPrice != that.productPrice) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (productCatalog != null ? !productCatalog.equals(that.productCatalog) : that.productCatalog != null)
            return false;
        if (productStock != null ? !productStock.equals(that.productStock) : that.productStock != null) return false;
        if (productColor != null ? !productColor.equals(that.productColor) : that.productColor != null) return false;
        if (productDesc != null ? !productDesc.equals(that.productDesc) : that.productDesc != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (productImg1 != null ? !productImg1.equals(that.productImg1) : that.productImg1 != null) return false;
        if (productImg2 != null ? !productImg2.equals(that.productImg2) : that.productImg2 != null) return false;
        if (productImg3 != null ? !productImg3.equals(that.productImg3) : that.productImg3 != null) return false;
        return productImg4 != null ? productImg4.equals(that.productImg4) : that.productImg4 == null;
    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productCatalog != null ? productCatalog.hashCode() : 0);
        result = 31 * result + productPrice;
        result = 31 * result + (productStock != null ? productStock.hashCode() : 0);
        result = 31 * result + (productColor != null ? productColor.hashCode() : 0);
        result = 31 * result + (productDesc != null ? productDesc.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (productImg1 != null ? productImg1.hashCode() : 0);
        result = 31 * result + (productImg2 != null ? productImg2.hashCode() : 0);
        result = 31 * result + (productImg3 != null ? productImg3.hashCode() : 0);
        result = 31 * result + (productImg4 != null ? productImg4.hashCode() : 0);
        return result;
    }
}
