package tw.com.ispan.ted.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ordersum", schema = "iii")
public class OrdersumBean {
    @Id
    @Basic
    @Column(name = "order_id", nullable = false, length = 20)
    private String orderId;
    @Basic
    @Column(name = "member_id", nullable = false)
    private int memberId;
    @Basic
    @Column(name = "status", nullable = false, length = 11)
    private String status;
    @Basic
    @Column(name = "pay_method", nullable = false, length = 15)
    private String payMethod;
    @Basic
    @Column(name = "create_date", nullable = true)
    private Date createDate;
    @Basic
    @Column(name = "order_total_amount", nullable = true, precision = 0)
    private java.math.BigDecimal orderTotalAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public java.math.BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(java.math.BigDecimal orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    @Override
    public String toString() {
        return "{" +
                "\"orderId\" : " + '\"' + orderId + '\"' +
                ", \"memberId\" : " + '\"'+ memberId + '\"' +
                ", \"status\" : " + '\"'+ status + '\"' +
                ", \"payMethod\" : " + '\"'+ payMethod + '\"' +
                ", \"createDate\" : " + '\"'+ createDate + '\"' +
                ", \"orderTotalAmount\" : " + '\"'+ orderTotalAmount + '\"' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersumBean that = (OrdersumBean) o;

        if (memberId != that.memberId) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (payMethod != null ? !payMethod.equals(that.payMethod) : that.payMethod != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (orderTotalAmount != null ? !orderTotalAmount.equals(that.orderTotalAmount) : that.orderTotalAmount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + memberId;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (payMethod != null ? payMethod.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (orderTotalAmount != null ? orderTotalAmount.hashCode() : 0);
        return result;
    }
}
