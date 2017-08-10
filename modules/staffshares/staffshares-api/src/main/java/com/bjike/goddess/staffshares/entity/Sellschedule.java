package com.bjike.goddess.staffshares.entity;

import com.bjike.goddess.common.api.entity.BaseEntity;
import javax.persistence.*;


/**
* 出售记录表
* @Author:			[ zhuangkaiqin ]
* @Date:			[  2017-08-04 10:15 ]
* @Description:	[ 出售记录表 ]
* @Version:		[ v1.0.0 ]
* @Copy:   		[ com.bjike ]
*/
@Entity
@Table(name = "staffshares_sellschedule")
public class Sellschedule extends BaseEntity { 

/**
* 出售人
*/
@Column(name = "sellName",nullable = false,columnDefinition = "VARCHAR(255)   COMMENT '出售人'"  ) 
 private String  sellName; 

/**
* 方案代码
*/
@Column(name = "code",nullable = false,columnDefinition = "VARCHAR(255)   COMMENT '方案代码'"  ) 
 private String  code; 

/**
* 方案名称
*/
@Column(name = "name",nullable = false,columnDefinition = "VARCHAR(255)   COMMENT '方案名称'"  ) 
 private String  name; 

/**
* 出售股数
*/
@Column(name = "",nullable = false,columnDefinition = "VARCHAR(255)   COMMENT '出售股数'"  ) 
 private int  sellNum; 

/**
* 出售价格
*/
@Column(name = "sellPrice",nullable = false,columnDefinition = "DECIMAL(10,2)   COMMENT '出售价格'"  ) 
 private Double  sellPrice; 

/**
* 出售金额
*/
@Column(name = "totalSellPrice",nullable = false,columnDefinition = "DECIMAL(10,2)   COMMENT '出售金额'"  ) 
 private Double  totalSellPrice; 

/**
* 出售时间
*/
@Column(name = "sellTime",nullable = false,columnDefinition = "DATETIME   COMMENT '出售时间'"  ) 
 private LocalDateTime  sellTime; 

/**
* 剩余出售量
*/
@Column(name = "",nullable = false,columnDefinition = "VARCHAR(255)   COMMENT '剩余出售量'"  ) 
 private int  number; 

/**
* 购买人
*/
@Column(name = "buyName",nullable = false,columnDefinition = "VARCHAR(255)   COMMENT '购买人'"  ) 
 private String  buyName; 

/**
* 购买股数
*/
@Column(name = "",nullable = false,columnDefinition = "VARCHAR(255)   COMMENT '购买股数'"  ) 
 private int  purchaseNum; 

/**
* 购买时间
*/
@Column(name = "buyTime",nullable = false,columnDefinition = "DATETIME   COMMENT '购买时间'"  ) 
 private LocalDateTime  buyTime; 



 public String getSellName () { 
 return sellName;
 } 
 public void setSellName (String sellName ) { 
 this.sellName = sellName ; 
 } 
 public String getCode () { 
 return code;
 } 
 public void setCode (String code ) { 
 this.code = code ; 
 } 
 public String getName () { 
 return name;
 } 
 public void setName (String name ) { 
 this.name = name ; 
 } 
 public int getSellNum () { 
 return sellNum;
 } 
 public void setSellNum (int sellNum ) { 
 this.sellNum = sellNum ; 
 } 
 public Double getSellPrice () { 
 return sellPrice;
 } 
 public void setSellPrice (Double sellPrice ) { 
 this.sellPrice = sellPrice ; 
 } 
 public Double getTotalSellPrice () { 
 return totalSellPrice;
 } 
 public void setTotalSellPrice (Double totalSellPrice ) { 
 this.totalSellPrice = totalSellPrice ; 
 } 
 public LocalDateTime getSellTime () { 
 return sellTime;
 } 
 public void setSellTime (LocalDateTime sellTime ) { 
 this.sellTime = sellTime ; 
 } 
 public int getNumber () { 
 return number;
 } 
 public void setNumber (int number ) { 
 this.number = number ; 
 } 
 public String getBuyName () { 
 return buyName;
 } 
 public void setBuyName (String buyName ) { 
 this.buyName = buyName ; 
 } 
 public int getPurchaseNum () { 
 return purchaseNum;
 } 
 public void setPurchaseNum (int purchaseNum ) { 
 this.purchaseNum = purchaseNum ; 
 } 
 public LocalDateTime getBuyTime () { 
 return buyTime;
 } 
 public void setBuyTime (LocalDateTime buyTime ) { 
 this.buyTime = buyTime ; 
 } 
 }