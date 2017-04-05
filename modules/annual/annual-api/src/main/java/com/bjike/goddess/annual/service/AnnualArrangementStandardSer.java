package com.bjike.goddess.annual.service;

import com.bjike.goddess.annual.bo.AnnualArrangementStandardBO;
import com.bjike.goddess.annual.dto.AnnualArrangementStandardDTO;
import com.bjike.goddess.annual.entity.AnnualArrangementStandard;
import com.bjike.goddess.annual.to.AnnualArrangementStandardTO;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.api.service.Ser;

import java.util.List;

/**
 * 年假层级标准业务接口
 *
 * @Author: [ dengjunren ]
 * @Date: [ 2017-03-27 04:33 ]
 * @Description: [ 年假层级标准业务接口 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
public interface AnnualArrangementStandardSer extends Ser<AnnualArrangementStandard, AnnualArrangementStandardDTO> {

    /**
     * 更新年假层级标准实体数据
     *
     * @param to 年假层级标准传输对象
     * @return
     * @throws SerException
     */
    default AnnualArrangementStandardBO update(AnnualArrangementStandardTO to) throws SerException {
        return null;
    }

    /**
     * 根据年假标准查询年假层级标准
     *
     * @param standard_id 年假标准id
     * @return
     * @throws SerException
     */
    default List<AnnualArrangementStandardBO> findByStandard(String standard_id) throws SerException {
        return null;
    }

    /**
     * 年假层级标准列表
     *
     * @param dto 年假层级标准数据传输对象
     * @return
     * @throws SerException
     */
    default List<AnnualArrangementStandardBO> maps(AnnualArrangementStandardDTO dto) throws SerException {
        return null;
    }

    /**
     * 根据标准和岗位层级查询年假层级标准
     *
     * @param standard_id    年假标准ID
     * @param arrangement_id 岗位层级ID
     * @return
     * @throws SerException
     */
    default AnnualArrangementStandardBO findByArrangementStandard(String standard_id, String arrangement_id) throws SerException {
        return null;
    }
}