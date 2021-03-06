package com.bjike.goddess.communicatemeeting.dao;

import com.bjike.goddess.common.jpa.dao.JpaRep;
import com.bjike.goddess.communicatemeeting.dto.CommunicateDTO;
import com.bjike.goddess.communicatemeeting.entity.Communicate;

/**
* 交流讨论持久化接口, 继承基类可使用ｊｐａ命名查询
* @Author:			[ chenjunhao ]
* @Date:			[  2017-05-27 02:00 ]
* @Description:	[ 交流讨论持久化接口, 继承基类可使用ｊｐａ命名查询 ]
* @Version:		[ v1.0.0 ]
* @Copy:   		[ com.bjike ]
*/
public interface CommunicateRep extends JpaRep<Communicate ,CommunicateDTO> { 

 }