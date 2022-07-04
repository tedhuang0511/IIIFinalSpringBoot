package tw.com.ispan.ted.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ordersumv2", schema = "iii")
public class Ordersumv2Bean {
    @Id
    @Basic
    @Column(name = "order_id", nullable = false, length = 20)
    private String orderId;
    @Basic
    @Column(name = "member_id", nullable = false)
    private int memberId;
    @Basic
    @Column(name = "deliver_cvs", nullable = true, length = 30)
    private String deliverCvs;
    @Basic
    @Column(name = "deliver_addr", nullable = true, length = 255)
    private String deliverAddr;
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

    public String getDeliverCvs() {
        return deliverCvs;
    }

    public void setDeliverCvs(String deliverCvs) {
        this.deliverCvs = deliverCvs;
    }

    public String getDeliverAddr() {
        return deliverAddr;
    }

    public void setDeliverAddr(String deliverAddr) {
        this.deliverAddr = deliverAddr;
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
                ",\"deliverCvs\" : " + '\"' + deliverCvs + '\"' +
                ",\"deliverAddr\" : " + '\"' + deliverAddr + '\"' +
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
        Ordersumv2Bean that = (Ordersumv2Bean) o;
        return memberId == that.memberId && Objects.equals(orderId, that.orderId) && Objects.equals(deliverCvs, that.deliverCvs) && Objects.equals(deliverAddr, that.deliverAddr) && Objects.equals(status, that.status) && Objects.equals(payMethod, that.payMethod) && Objects.equals(createDate, that.createDate) && Objects.equals(orderTotalAmount, that.orderTotalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, memberId, deliverCvs, deliverAddr, status, payMethod, createDate, orderTotalAmount);
    }
}
