package tw.com.ispan.ted.domain;

import javax.persistence.*;

@Entity
@Table(name = "productsale", schema = "iii")
//@NamedQueries(
//        value = {
//                @NamedQuery(
//                        name="byOrderId",
//                        query="from com.ted.model.MemberOrderBean where orderId = :orderId"
//                )
//        }
//)
public class ProductsaleBean {
    @Basic
    @Column(name = "product_name", nullable = false, length = 30)
    private String productName;
    @Basic
    @Column(name = "product_catalog", nullable = false, length = 15)
    private String productCatalog;
    @Basic
    @Column(name = "product_img1", nullable = true, length = 255)
    private String productImg1;
    @Id
    @Basic
    @Column(name = "create_date", nullable = true)
    private java.util.Date createDate;
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

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

    public String getProductImg1() {
        return productImg1;
    }

    public void setProductImg1(String productImg1) {
        this.productImg1 = productImg1;
    }

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductsaleBean that = (ProductsaleBean) o;

        if (quantity != that.quantity) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (productCatalog != null ? !productCatalog.equals(that.productCatalog) : that.productCatalog != null)
            return false;
        if (productImg1 != null ? !productImg1.equals(that.productImg1) : that.productImg1 != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productName != null ? productName.hashCode() : 0;
        result = 31 * result + (productCatalog != null ? productCatalog.hashCode() : 0);
        result = 31 * result + (productImg1 != null ? productImg1.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }
}
