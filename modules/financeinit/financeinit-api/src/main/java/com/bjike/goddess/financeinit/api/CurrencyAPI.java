package com.bjike.goddess.financeinit.api;

import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.financeinit.bo.CurrencyBO;
import com.bjike.goddess.financeinit.dto.CurrencyDTO;
import com.bjike.goddess.financeinit.to.CurrencyTO;
import com.bjike.goddess.financeinit.to.GuidePermissionTO;

import java.util.List;

/**
 * 币别业务接口
 *
 * @Author: [ tanghaixiang ]
 * @Date: [ 2017-03-29 03:53 ]
 * @Description: [ 币别业务接口 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
public interface CurrencyAPI {


    /**
     * 下拉导航权限
     */
    default Boolean sonPermission() throws SerException {
        return null;
    }
    /**
     * 导航权限
     */
    default Boolean guidePermission(GuidePermissionTO guidePermissionTO) throws SerException {
        return null;
    }


    /**
     * 币别列表总条数
     *
     */
    default Long countCurrency(CurrencyDTO currencyDTO) throws SerException {
        return null;
    }

    /**
     * 根据id获取币别列表
     * @return class CurrencyBO
     */
    default CurrencyBO getOneById(String id) throws SerException {return null;}

    /**
     *  币别列表
     * @return class CurrencyBO
     */
    default List<CurrencyBO> listCurrency(CurrencyDTO currencyDTO) throws SerException {return null;}
    /**
     *  添加
     * @param currencyTO  币别信息
     * @return class CurrencyBO
     */
    default CurrencyBO addCurrency(CurrencyTO currencyTO) throws SerException { return null;}

    /**
     *  编辑
     * @param currencyTO  币别信息
     * @return class CurrencyBO
     */
    default CurrencyBO editCurrency(CurrencyTO currencyTO) throws SerException { return null;}

    /**
     * 删除级别
     * @param id id
     */
    default void deleteCurrency(String id ) throws SerException {return;};
}