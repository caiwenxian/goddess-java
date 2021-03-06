package com.bjike.goddess.festival.action.festival;

import com.bjike.goddess.common.api.exception.ActException;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.api.restful.Result;
import com.bjike.goddess.common.consumer.interceptor.login.LoginAuth;
import com.bjike.goddess.common.consumer.restful.ActResult;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.festival.api.HolidayProgrammeAPI;
import com.bjike.goddess.festival.bo.HolidayProgrammeBO;
import com.bjike.goddess.festival.dto.HolidayProgrammeDTO;
import com.bjike.goddess.festival.to.HolidayProgrammeTO;
import com.bjike.goddess.festival.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 法定节假日放假方案
 *
 * @Author: [ tanghaixiang ]
 * @Date: [ 2017-04-01 09:03 ]
 * @Description: [ 法定节假日放假方案 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@RestController
@RequestMapping("holidayprogramme")
public class HolidayProgrammeAction {

    @Autowired
    private HolidayProgrammeAPI holidayProgrammeAPI;

    /**
     *  法定节假日放假方案列表总条数
     *
     * @param holidayProgrammeDTO  法定节假日放假方案信息dto
     * @des 获取所有法定节假日放假方案信息总条数
     * @version v1
     */
    @GetMapping("v1/count")
    public Result count(HolidayProgrammeDTO holidayProgrammeDTO) throws ActException {
        try {
            Long count = holidayProgrammeAPI.countHolidayProgramme(holidayProgrammeDTO);
            return ActResult.initialize(count);
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 一个放假方案
     *
     * @param id 放假方案id
     * @des 根据id获取一个放假方案
     * @return  class HolidayProgrammeVO
     * @version v1
     */
    @GetMapping("v1/getOneById/{id}")
    public Result getOneById(@PathVariable String id) throws ActException {
        try {
            HolidayProgrammeBO holidayProgrammeBO = holidayProgrammeAPI.getOneById(id);
            HolidayProgrammeVO holidayProgrammeVO = BeanTransform.copyProperties(
                    holidayProgrammeBO, HolidayProgrammeVO.class);
            //节假日工作安排数组
            List<HolidayWorkPlanVO> holidayListBO = BeanTransform.copyProperties(holidayProgrammeBO.getHolidayWorkPlanBOList(),HolidayWorkPlanVO.class);
            //各地区紧急联系人数
            List<AreaRelationerVO> areaListBO = BeanTransform.copyProperties(holidayProgrammeBO.getAreaRelationerBOList(),AreaRelationerVO.class);
            //节假日福利数组
            List<WelfareVO> welfareListBO = BeanTransform.copyProperties(holidayProgrammeBO.getWelfareBOList(),WelfareVO.class);
            //注意事项数组
            List<NoticeThingVO> noticeListBO = BeanTransform.copyProperties(holidayProgrammeBO.getNoticeThingBOList(), NoticeThingVO.class);

            holidayProgrammeVO.setHolidayWorkPlanVOList( holidayListBO );
            holidayProgrammeVO.setAreaRelationerVOList( areaListBO );
            holidayProgrammeVO.setWelfareVOList( welfareListBO );
            holidayProgrammeVO.setNoticeThingVOList( noticeListBO);
            return ActResult.initialize(holidayProgrammeVO);
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 法定节假日放假方案列表
     *
     * @param holidayProgrammeDTO 法定节假日放假方案信息dto
     * @des 获取所有法定节假日放假方案信息
     * @return  class HolidayProgrammeVO
     * @version v1
     */
    @GetMapping("v1/list")
    public Result findListHolidayProgramme(HolidayProgrammeDTO holidayProgrammeDTO, BindingResult bindingResult, HttpServletRequest request) throws ActException {
        try {
            List<HolidayProgrammeVO> holidayProgrammeVOList = BeanTransform.copyProperties(
                    holidayProgrammeAPI.listHolidayProgramme(holidayProgrammeDTO), HolidayProgrammeVO.class, request);
            return ActResult.initialize(holidayProgrammeVOList);
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 添加法定节假日放假方案
     *
     * @param holidayProgrammeTO 法定节假日放假方案基本信息数据to
     * @des 添加法定节假日放假方案
     * @return  class HolidayProgrammeVO
     * @version v1
     */
    @LoginAuth
    @PostMapping("v1/add")
    public Result addHolidayProgramme(@Validated({HolidayProgrammeTO.TESTAddAndEdit.class}) HolidayProgrammeTO holidayProgrammeTO, BindingResult bindingResult) throws ActException {
        try {
            HolidayProgrammeBO holidayProgrammeBO1 = holidayProgrammeAPI.addHolidayProgramme(holidayProgrammeTO);
            return ActResult.initialize(BeanTransform.copyProperties(holidayProgrammeBO1,HolidayProgrammeVO.class));
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }


    /**
     * 编辑法定节假日放假方案
     *
     * @param holidayProgrammeTO 法定节假日放假方案基本信息数据bo
     * @des 添加法定节假日放假方案
     * @return  class HolidayProgrammeVO
     * @version v1
     */
    @LoginAuth
    @PutMapping("v1/edit")
    public Result editHolidayProgramme(@Validated({HolidayProgrammeTO.TESTAddAndEdit.class}) HolidayProgrammeTO holidayProgrammeTO) throws ActException {
        try {
            HolidayProgrammeBO holidayProgrammeBO1 = holidayProgrammeAPI.editHolidayProgramme(holidayProgrammeTO);
            return ActResult.initialize(BeanTransform.copyProperties(holidayProgrammeBO1,HolidayProgrammeVO.class));
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param id id
     * @des 根据id删除法定节假日放假方案信息记录
     * @version v1
     */
    @LoginAuth
    @DeleteMapping("v1/delete/{id}")
    public Result deleteHolidayProgramme(@PathVariable String id) throws ActException {
        try {
            holidayProgrammeAPI.deleteHolidayProgramme(id);
            return new ActResult("delete success!");
        } catch (SerException e) {
            throw new ActException("删除失败："+e.getMessage());
        }
    }
}