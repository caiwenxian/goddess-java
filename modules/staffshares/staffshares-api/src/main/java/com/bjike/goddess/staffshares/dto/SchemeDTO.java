package com.bjike.goddess.staffshares.dto;

import com.bjike.goddess.common.api.dto.BaseDTO;
import com.bjike.goddess.staffshares.enums.Status;
import com.bjike.goddess.staffshares.enums.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工持股管理数据传输对象
 *
 * @Author: [ zhuangkaiqin ]
 * @Date: [ 2017-08-04 08:59 ]
 * @Description: [ 员工持股管理数据传输对象 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
public class SchemeDTO extends BaseDTO {
    /**
     * 审核状态
     */
    private Status status;

    /**
     * 股份种类
     */
    private Type type;

    /**
     * 方案代码
     */
    private String code;

    /**
     * 方案名称
     */
    private String name;

    /**
     * 发行目的
     */
    private String aim;

    /**
     * 总资本
     */
    private Double totalCapital;

    /**
     * 总股本
     */
    private Double totalEquity;

    /**
     * 票面价值
     */
    private Double facevalue;

    /**
     * 计划筹资额
     */
    private Double planCapital;

    /**
     * 发行人
     */
    private String publisher;

    /**
     * 发行性质
     */
    private String issue;

    /**
     * 发行比例
     */
    private Double proportion;

    /**
     * 发行数量
     */
    private int number;

    /**
     * 发行价格
     */
    private Double price;

    /**
     * 发行对象
     */
    private String object;

    /**
     * 发行方式
     */
    private String mode;

    /**
     * 发行时间
     */
    private LocalDate time;

    /**
     * 出售方式
     */
    private String method;

    /**
     * 申购上下限标准
     */
    private String standards;

    /**
     * 分红核算时间
     */
    private LocalDate accountingTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 方案制定时间
     */
    private LocalDateTime programmeTime;

    /**
     * 方案制定人
     */
    private String setters;

    /**
     * 总经办
     */
    private String manager;

    /**
     * 审核意见
     */
    private String opinion;

    /**
     * 剩余出售股数
     */
    private int sharesNum;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public Double getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(Double totalCapital) {
        this.totalCapital = totalCapital;
    }

    public Double getTotalEquity() {
        return totalEquity;
    }

    public void setTotalEquity(Double totalEquity) {
        this.totalEquity = totalEquity;
    }

    public Double getFacevalue() {
        return facevalue;
    }

    public void setFacevalue(Double facevalue) {
        this.facevalue = facevalue;
    }

    public Double getPlanCapital() {
        return planCapital;
    }

    public void setPlanCapital(Double planCapital) {
        this.planCapital = planCapital;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStandards() {
        return standards;
    }

    public void setStandards(String standards) {
        this.standards = standards;
    }

    public LocalDate getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(LocalDate accountingTime) {
        this.accountingTime = accountingTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getProgrammeTime() {
        return programmeTime;
    }

    public void setProgrammeTime(LocalDateTime programmeTime) {
        this.programmeTime = programmeTime;
    }

    public String getSetters() {
        return setters;
    }

    public void setSetters(String setters) {
        this.setters = setters;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public int getSharesNum() {
        return sharesNum;
    }

    public void setSharesNum(int sharesNum) {
        this.sharesNum = sharesNum;
    }
}