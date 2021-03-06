package com.bjike.goddess.organize.api;

import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.organize.bo.ReflectBO;
import com.bjike.goddess.organize.dto.ReflectDTO;
import com.bjike.goddess.organize.to.ReflectTO;

import java.util.List;

/**
 * 对外体现类型业务接口
 *
 * @Author: [dengjunren]
 * @Date: [2017-03-08 17:31]
 * @Description: [ ]
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
public interface ReflectAPI {

    /**
     * 查询未冻结体现类型
     *
     * @return
     * @throws SerException
     */
    default List<ReflectBO> findStatus() throws SerException {
        return null;
    }

    /**
     * 保存体现类型
     *
     * @param to
     * @return
     * @throws SerException
     */
    default ReflectBO save(ReflectTO to) throws SerException {
        return null;
    }

    /**
     * 修改体现类型
     *
     * @param to
     * @return
     * @throws SerException
     */
    default ReflectBO update(ReflectTO to) throws SerException {
        return null;
    }

    /**
     * 删除
     *
     * @param id 体现类型数据id
     * @return
     * @throws SerException
     */
    default ReflectBO delete(String id) throws SerException {
        return null;
    }

    /**
     * 关闭
     *
     * @param id 体现类型数据id
     * @return
     * @throws SerException
     */
    default ReflectBO close(String id) throws SerException {
        return null;
    }

    /**
     * 开启
     *
     * @param id 体现类型数据id
     * @return
     * @throws SerException
     */
    default ReflectBO open(String id) throws SerException {
        return null;
    }

    /**
     * 列表
     *
     * @param dto 体现类型数据传输
     * @return
     * @throws SerException
     */
    default List<ReflectBO> maps(ReflectDTO dto) throws SerException {
        return null;
    }

    /**
     * 获取总条数
     *
     * @return
     * @throws SerException
     */
    default Long getTotal() throws SerException {
        return null;
    }

    /**
     * 根据id查询体现类型数据
     *
     * @param id 体现类型数据id
     * @return
     * @throws SerException
     */
    default ReflectBO findById(String id) throws SerException {
        return null;
    }

}
