package com.bjike.goddess.capability.entity;

import com.bjike.goddess.common.api.entity.BaseEntity;

import javax.persistence.*;


/**
 * 尚在进行中项目数
 *
 * @Author: [ zhuangkaiqin ]
 * @Date: [ 2017-06-16 06:23 ]
 * @Description: [ 尚在进行中项目数 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@Entity
@Table(name = "capability_inprojct")
public class InProjct extends BaseEntity {

    /**
     * 主表Id
     */
    @Column(name = "baseId", nullable = false, columnDefinition = "VARCHAR(255)   COMMENT '主表Id'")
    private String baseId;

    /**
     * 名字
     */
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)   COMMENT '名字'")
    private String name;


    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}