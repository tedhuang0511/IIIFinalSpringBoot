package tw.com.ispan.ted.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "members", schema = "iii")
public class MembersBean {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "member_id", nullable = false)
    private Integer memberId;
    @Basic
    @Column(name = "member_accouunt", nullable = false, length = 20)
    private String memberAccouunt;
    @Basic
    @Column(name = "member_password", nullable = false, length = 100)
    private String memberPassword;
    @Basic
    @Column(name = "member_lastname", nullable = false, length = 20)
    private String memberLastname;
    @Basic
    @Column(name = "member_firstname", nullable = false, length = 20)
    private String memberFirstname;
    @Basic
    @Column(name = "member_gender", nullable = false, length = 20)
    private String memberGender;
    @Basic
    @Column(name = "member_nickname", nullable = false, length = 20)
    private String memberNickname;
    @Basic
    @Column(name = "member_email", nullable = false, length = 20)
    private String memberEmail;
    @Basic
    @Column(name = "member_tel", nullable = false, length = 20)
    private String memberTel;
    @Basic
    @Column(name = "member_addr", nullable = false, length = 100)
    private String memberAddr;
    @Basic
    @Column(name = "member_birth", nullable = false)
    private Date memberBirth;
    @Basic
    @Column(name = "create_user", length = 20)
    private String createUser;
    @Basic
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Basic
    @Column(name = "update_user",  length = 20)
    private String updateUser;
    @Basic
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberAccouunt() {
        return memberAccouunt;
    }

    public void setMemberAccouunt(String memberAccouunt) {
        this.memberAccouunt = memberAccouunt;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberLastname() {
        return memberLastname;
    }

    public void setMemberLastname(String memberLastname) {
        this.memberLastname = memberLastname;
    }

    public String getMemberFirstname() {
        return memberFirstname;
    }

    public void setMemberFirstname(String memberFirstname) {
        this.memberFirstname = memberFirstname;
    }

    public String getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getMemberAddr() {
        return memberAddr;
    }

    public void setMemberAddr(String memberAddr) {
        this.memberAddr = memberAddr;
    }

    public Date getMemberBirth() {
        return memberBirth;
    }

    public void setMemberBirth(Date memberBirth) {
        this.memberBirth = memberBirth;
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

    @Override
    public String toString() {
        return "{" +
                "\"memberId\" : " + '\"' + memberId + '\"' +
                ", \"memberAccouunt\" : " + '\"'+ memberAccouunt + '\"' +
                ", \"memberLastname\" : " + '\"'+ memberLastname + '\"' +
                ", \"memberFirstname\" : " + '\"'+ memberFirstname + '\"' +
                ", \"memberEmail\" : " + '\"'+ memberEmail + '\"' +
                ", \"memberTel\" : " + '\"'+ memberTel + '\"' +
                ", \"memberAddr\" : " + '\"'+ memberAddr + '\"' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MembersBean that = (MembersBean) o;

        if (memberId != that.memberId) return false;
        if (memberAccouunt != null ? !memberAccouunt.equals(that.memberAccouunt) : that.memberAccouunt != null)
            return false;
        if (memberPassword != null ? !memberPassword.equals(that.memberPassword) : that.memberPassword != null)
            return false;
        if (memberLastname != null ? !memberLastname.equals(that.memberLastname) : that.memberLastname != null)
            return false;
        if (memberFirstname != null ? !memberFirstname.equals(that.memberFirstname) : that.memberFirstname != null)
            return false;
        if (memberGender != null ? !memberGender.equals(that.memberGender) : that.memberGender != null) return false;
        if (memberNickname != null ? !memberNickname.equals(that.memberNickname) : that.memberNickname != null)
            return false;
        if (memberEmail != null ? !memberEmail.equals(that.memberEmail) : that.memberEmail != null) return false;
        if (memberTel != null ? !memberTel.equals(that.memberTel) : that.memberTel != null) return false;
        if (memberAddr != null ? !memberAddr.equals(that.memberAddr) : that.memberAddr != null) return false;
        if (memberBirth != null ? !memberBirth.equals(that.memberBirth) : that.memberBirth != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = memberId;
        result = 31 * result + (memberAccouunt != null ? memberAccouunt.hashCode() : 0);
        result = 31 * result + (memberPassword != null ? memberPassword.hashCode() : 0);
        result = 31 * result + (memberLastname != null ? memberLastname.hashCode() : 0);
        result = 31 * result + (memberFirstname != null ? memberFirstname.hashCode() : 0);
        result = 31 * result + (memberGender != null ? memberGender.hashCode() : 0);
        result = 31 * result + (memberNickname != null ? memberNickname.hashCode() : 0);
        result = 31 * result + (memberEmail != null ? memberEmail.hashCode() : 0);
        result = 31 * result + (memberTel != null ? memberTel.hashCode() : 0);
        result = 31 * result + (memberAddr != null ? memberAddr.hashCode() : 0);
        result = 31 * result + (memberBirth != null ? memberBirth.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
