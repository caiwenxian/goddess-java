package com.bjike.goddess.moneyside.api;

import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.moneyside.bo.FundEntryConfirmedBO;
import com.bjike.goddess.moneyside.dto.FundEntryConfirmedDTO;

import java.util.List;

/**
 * 资金进入申请已确认业务接口
 *
 * @Author: [ xiazhili ]
 * @Date: [ 2017-06-05 11:08 ]
 * @Description: [ 资金进入申请已确认业务接口 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
public interface FundEntryConfirmedAPI {

    /**
     * 资金进入申请已确认列表总条数
     */
    default Long countFundEntryConfirmed(FundEntryConfirmedDTO fundEntryConfirmedDTO) throws SerException {
        return null;
    }

    /**
     * 一个资金进入申请已确认
     *
     * @return class FundEntryConfirmedBO
     */
    default FundEntryConfirmedBO getOne(String id) throws SerException {
        return null;
    }

    /**
     * 资金进入申请已确认
     *
     * @param fundEntryConfirmedDTO 资金进入申请已确认dto
     * @return class FundEntryConfirmedBO
     * @throws SerException
     */
    default List<FundEntryConfirmedBO> findListFundEntryConfirmed(FundEntryConfirmedDTO fundEntryConfirmedDTO) throws SerException {
        return null;
    }

    /**
     * 根据id删除资金进入申请已确认
     *
     * @param id
     * @throws SerException
     */
    default void removeFundEntryConfirmed(String id) throws SerException {

    }
}