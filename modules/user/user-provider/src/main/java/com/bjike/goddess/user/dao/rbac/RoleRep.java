package com.bjike.goddess.user.dao.rbac;

import com.bjike.goddess.common.jpa.dao.JpaRep;
import com.bjike.goddess.user.dto.rbac.RoleDTO;
import com.bjike.goddess.user.entity.rbac.Role;

/**
 * 角色持久化接口,继承基类可使用ｊｐａ命名查询
 *
 * @Author: [liguiqin]
 * @Date: [2016-11-23 15:47]
 * @Description: []
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
public interface RoleRep extends JpaRep<Role, RoleDTO> {

}
