package tw.com.ispan.ted.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "member_order", schema = "iii")
@NamedQueries(
        value = {
                @NamedQuery(
                        name="byOrderId",
                        query="from tw.com.ispan.ted.domain.MemberOrderBean where orderId = :orderId"
                )
        }
)
public class MemberOrderBean {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "seqno", nullable = false)
    private int seqno;
    @Basic
    @Column(name = "order_id", nullable = false, length = 30)
    private String orderId;
    @Basic
    @Column(name = "member_id", nullable = false)
    private int memberId;
    @Basic
    @Column(name = "pay_method", nullable = false, length = 15)
    private String payMethod;
    @Basic
    @Column(name = "status", nullable = false, length = 5)
    private String status = "01"; //insert時預設給01
    @Basic
    @Column(name = "delivered_date")
    private Date deliveredDate;
    @Basic
    @Column(name = "deliver_cvs", length = 30)
    private String deliverCvs = "";
    @Basic
    @Column(name = "deliver_addr")
    private String deliverAddr = "";
    @Basic
    @Column(name = "received_date")
    private Date receivedDate;
    @Basic
    @Column(name = "create_date")
    private Date createDate;
    @Basic
    @Column(name = "create_user", length = 30)
    private String createUser;
    @Basic
    @Column(name = "update_user", length = 30)
    private String updateUser;
    @Basic
    @Column(name = "update_date")
    private Date updateDate;

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

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

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
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

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    @Override
    public String toString() {
        return "{" +
                "\"訂單編號\" : " + '\"' + orderId + '\"' +
                ", \"會員編號\" : " + '\"'+ memberId + '\"' +
                ", \"付款方式\" : " + '\"'+ payMethod + '\"' +
                ", \"狀態\" : " + '\"'+ status + '\"' +
                ", \"訂單建立日期\" : " + '\"'+ createDate + '\"' +
                ", \"出貨日期\" : " + '\"'+ deliveredDate + '\"' +
                ", \"到貨超商\" : " + '\"'+ deliverCvs + '\"' +
                ", \"宅配地址\" : " + '\"'+ deliverAddr + '\"' +
                ", \"已交付日期\" : " + '\"'+ receivedDate + '\"' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberOrderBean that = (MemberOrderBean) o;

        if (seqno != that.seqno) return false;
        if (memberId != that.memberId) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (payMethod != null ? !payMethod.equals(that.payMethod) : that.payMethod != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (deliveredDate != null ? !deliveredDate.equals(that.deliveredDate) : that.deliveredDate != null)
            return false;
        if (deliverCvs != null ? !deliverCvs.equals(that.deliverCvs) : that.deliverCvs != null) return false;
        if (deliverAddr != null ? !deliverAddr.equals(that.deliverAddr) : that.deliverAddr != null) return false;
        if (receivedDate != null ? !receivedDate.equals(that.receivedDate) : that.receivedDate != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = seqno;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + memberId;
        result = 31 * result + (payMethod != null ? payMethod.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (deliveredDate != null ? deliveredDate.hashCode() : 0);
        result = 31 * result + (deliverCvs != null ? deliverCvs.hashCode() : 0);
        result = 31 * result + (deliverAddr != null ? deliverAddr.hashCode() : 0);
        result = 31 * result + (receivedDate != null ? receivedDate.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
