package com.bjike.goddess.buyticket.entity;

import com.bjike.goddess.common.api.entity.BaseEntity;
import com.bjike.goddess.common.api.type.Status;

import javax.persistence.*;


/**
 * 车票信息记录
 *
 * @Author: [ xiazhili ]
 * @Date: [ 2017-04-25 03:48 ]
 * @Description: [ 车票信息记录 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@Entity
@Table(name = "buyticket_ticketinforecord")
public class TicketInfoRecord extends BaseEntity {

    /**
     * 网站名称
     */
    @Column(name = "siteName", columnDefinition = "VARCHAR(255)   COMMENT '网站名称'")
    private String siteName;

    /**
     * 网址
     */
    @Column(name = "url", columnDefinition = "VARCHAR(255)   COMMENT '网址'")
    private String url;

    /**
     * 账号
     */
    @Column(name = "account", columnDefinition = "VARCHAR(255)   COMMENT '账号'")
    private String account;

    /**
     * 密码
     */
    @Column(name = "password", columnDefinition = "VARCHAR(255)   COMMENT '密码'")
    private String password;

    /**
     * 注册人
     */
    @Column(name = "registrant", columnDefinition = "VARCHAR(255)   COMMENT '注册人'")
    private String registrant;

    /**
     * 注册信息
     */
    @Column(name = "registrationInfo", columnDefinition = "VARCHAR(255)   COMMENT '注册信息'")
    private String registrationInfo;

    /**
     * 备注
     */
    @Column(name = "remark", columnDefinition = "VARCHAR(255)   COMMENT '备注'")
    private String remark;

    /**
     * 状态
     */
    @Column(columnDefinition = "TINYINT(2) DEFAULT 0 COMMENT '状态'", nullable = false, insertable = false)
    private Status status;


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public String getRegistrationInfo() {
        return registrationInfo;
    }

    public void setRegistrationInfo(String registrationInfo) {
        this.registrationInfo = registrationInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}