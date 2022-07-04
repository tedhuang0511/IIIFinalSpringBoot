package tw.com.ispan.ted.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_detail", schema = "iii")
public class OrderDetailBean {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_det_seqno", nullable = false)
    private int orderDetSeqno;
    @Basic
    @Column(name = "order_id", nullable = false, length = 20)
    private String orderId;
    @Basic
    @Column(name = "product_id", nullable = false)
    private int productId;
    @Basic
    @Column(name = "unit_price", nullable = false)
    private int unitPrice;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;

    public int getOrderDetSeqno() {
        return orderDetSeqno;
    }

    public void setOrderDetSeqno(int orderDetSeqno) {
        this.orderDetSeqno = orderDetSeqno;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "{" +
                "\"orderId\" : " + '\"' + orderId + '\"' +
                ", \"productId\" : " + '\"'+ productId + '\"' +
                ", \"unitPrice\" : " + '\"'+ unitPrice + '\"' +
                ", \"quantity\" : " + '\"'+ quantity + '\"' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailBean that = (OrderDetailBean) o;

        if (orderDetSeqno != that.orderDetSeqno) return false;
        if (productId != that.productId) return false;
        if (unitPrice != that.unitPrice) return false;
        if (quantity != that.quantity) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderDetSeqno;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + productId;
        result = 31 * result + unitPrice;
        result = 31 * result + quantity;
        return result;
    }
}
