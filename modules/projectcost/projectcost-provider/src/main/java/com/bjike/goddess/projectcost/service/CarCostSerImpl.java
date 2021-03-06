package com.bjike.goddess.projectcost.service;

import com.bjike.goddess.common.api.dto.Restrict;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.jpa.service.ServiceImpl;
import com.bjike.goddess.common.provider.utils.RpcTransmit;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.common.utils.date.DateUtil;
import com.bjike.goddess.dispatchcar.api.DispatchCarInfoAPI;
import com.bjike.goddess.dispatchcar.bo.DispatchCarInfoBO;
import com.bjike.goddess.dispatchcar.to.ConditionTO;
import com.bjike.goddess.projectcost.bo.CarCostBO;
import com.bjike.goddess.projectcost.dto.CarCostDTO;
import com.bjike.goddess.projectcost.entity.CarCost;
import com.bjike.goddess.projectcost.enums.GuideAddrStatus;
import com.bjike.goddess.projectcost.to.CarCostTO;
import com.bjike.goddess.projectcost.to.FindTO;
import com.bjike.goddess.projectcost.to.GuidePermissionTO;
import com.bjike.goddess.user.api.UserAPI;
import com.bjike.goddess.user.bo.UserBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 车辆费用业务实现
 *
 * @Author: [ 邓钧仁 ]
 * @Date: [ 2017-05-04 05:14 ]
 * @Description: [ 车辆费用业务实现 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@CacheConfig(cacheNames = "projectcostSerCache")
@Service
public class CarCostSerImpl extends ServiceImpl<CarCost, CarCostDTO> implements CarCostSer {

    @Autowired
    private DispatchCarInfoAPI dispatchCarInfoAPI;
    @Autowired
    private UserAPI userAPI;
    @Autowired
    private CusPermissionSer cusPermissionSer;

    @Override
    public CarCostBO save(CarCostTO to) throws SerException {
        CarCost entity = BeanTransform.copyProperties(to, CarCost.class);
        this.countCost(entity);
        super.save(entity);
        return BeanTransform.copyProperties(entity, CarCostBO.class);
    }

    private void countCost(CarCost entity) throws SerException {
        ConditionTO to = BeanTransform.copyProperties(entity, ConditionTO.class);
        String format;
//        if (entity.getMonth() >= 10)
//            format = "%d-%d-01";
//        else
//            format = "$d-0%d-01";

//        LocalDate start = LocalDate.parse(String.format(format, entity.getYear(), entity.getMonth()));
        LocalDate start = DateUtil.getStartDayOfMonth(entity.getYear(),entity.getMonth());
        LocalDate end = start.withDayOfMonth(start.getMonth().maxLength());
        to.setDispatchStartDate(start.toString());
        to.setDispatchEndDate(end.toString());

        List<DispatchCarInfoBO> list = dispatchCarInfoAPI.getByConfition(to);
        if (null != list)
            entity.setActualDegree(list.size());
        else
            entity.setActualDegree(0);
        entity.setBalanceDegree(entity.getTargetDegree() - entity.getActualDegree() + 0d);
        entity.setTargetCost(entity.getTargetDegree() * entity.getUnivalent());
        entity.setActualCost(entity.getActualDegree() * entity.getUnivalent());
        entity.setBalanceCost(entity.getTargetCost() - entity.getActualCost());
    }

    @Override
    public CarCostBO update(CarCostTO to) throws SerException {
        CarCost entity = super.findById(to.getId());
        if (null == entity)
            throw new SerException("该数据不存在");
        BeanTransform.copyProperties(to, entity, true);
        this.countCost(entity);
        entity.setModifyTime(LocalDateTime.now());
        super.update(entity);
        return BeanTransform.copyProperties(entity, CarCostBO.class);
    }

    @Override
    public CarCostBO delete(String id) throws SerException {
        CarCost entity = super.findById(id);
        if (null == entity)
            throw new SerException("该数据不存在");
        super.remove(entity);
        return BeanTransform.copyProperties(entity, CarCostBO.class);
    }

    @Override
    public List<CarCostBO> maps(CarCostDTO dto) throws SerException {
        dto.getSorts().add("year=desc");
        dto.getSorts().add("month=desc");
        dto.getSorts().add("area=desc");
        dto.getSorts().add("project=desc");
        dto.getSorts().add("name=desc");
        return BeanTransform.copyProperties(super.findByPage(dto), CarCostBO.class);
    }

    @Override
    public CarCostBO updateActual(CarCostTO to) throws SerException {
        CarCost entity = super.findById(to.getId());
        if (null == entity)
            throw new SerException("该数据不存在");
        BeanTransform.copyProperties(to, entity, true);
        entity.setBalanceDegree(entity.getTargetDegree() - entity.getActualDegree() + 0d);
        entity.setTargetCost(entity.getTargetDegree() * entity.getUnivalent());
        entity.setActualCost(entity.getActualDegree() * entity.getUnivalent());
        entity.setBalanceCost(entity.getTargetCost() - entity.getActualCost());
        entity.setModifyTime(LocalDateTime.now());
        super.update(entity);
        return BeanTransform.copyProperties(entity, CarCostBO.class);
    }

    @Override
    public CarCostBO getById(String id) throws SerException {
        CarCost entity = super.findById(id);
        if (null == entity)
            throw new SerException("该数据不存在");
        return BeanTransform.copyProperties(entity, CarCostBO.class);
    }

    @Override
    public Long getTotal() throws SerException {
        CarCostDTO dto = new CarCostDTO();
        return super.count(dto);
    }

    @Override
    public List<CarCostBO> findByTO(FindTO to) throws SerException {
        CarCostDTO dto = new CarCostDTO();
        if (StringUtils.isNotBlank(to.getArea()))
            dto.getConditions().add(Restrict.eq("area", to.getArea()));
        if (StringUtils.isNotBlank(to.getProject()))
            dto.getConditions().add(Restrict.eq("project", to.getProject()));
        if (StringUtils.isNotBlank(to.getName()))
            dto.getConditions().add(Restrict.eq("name", to.getName()));
        if (null != to.getMonth())
            dto.getConditions().add(Restrict.eq("month", to.getMonth()));
        if (null != to.getYear())
            dto.getConditions().add(Restrict.eq("year", to.getYear()));
        return BeanTransform.copyProperties(super.findByCis(dto), CarCostBO.class);
    }

    @Override
    public Boolean sonPermission() throws SerException {
        String userToken = RpcTransmit.getUserToken();
        Boolean flagSee = guideSeeIdentity();
        RpcTransmit.transmitUserToken(userToken);
        Boolean flagAdd = guideAddIdentity();
        if( flagSee || flagAdd ){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean guidePermission(GuidePermissionTO guidePermissionTO) throws SerException {
        String userToken = RpcTransmit.getUserToken();
        GuideAddrStatus guideAddrStatus = guidePermissionTO.getGuideAddrStatus();
        Boolean flag = true;
        switch (guideAddrStatus) {
            case LIST:
                flag = guideSeeIdentity();
                break;
            case ADD:
                flag = guideAddIdentity();
                break;
            case EDIT:
                flag = guideAddIdentity();
                break;
            case AUDIT:
                flag = guideAddIdentity();
                break;
            case DELETE:
                flag = guideAddIdentity();
                break;
            case CONGEL:
                flag = guideAddIdentity();
                break;
            case THAW:
                flag = guideAddIdentity();
                break;
            case COLLECT:
                flag = guideAddIdentity();
                break;
            case IMPORT:
                flag = guideAddIdentity();
                break;
            case EXPORT:
                flag = guideAddIdentity();
                break;
            case UPLOAD:
                flag = guideAddIdentity();
                break;
            case DOWNLOAD:
                flag = guideAddIdentity();
                break;
            case SEE:
                flag = guideSeeIdentity();
                break;
            case SEEFILE:
                flag = guideSeeIdentity();
                break;
            default:
                flag = true;
                break;
        }
        return flag;
    }


    /**
     * 导航栏核对查看权限（部门级别）
     */
    private Boolean guideSeeIdentity() throws SerException {
        Boolean flag = false;
        String userToken = RpcTransmit.getUserToken();
        UserBO userBO = userAPI.currentUser();
        RpcTransmit.transmitUserToken(userToken);
        String userName = userBO.getUsername();
        if (!"admin".equals(userName.toLowerCase())) {
            flag = cusPermissionSer.busCusPermission("2");
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 导航栏核对添加修改删除审核权限（岗位级别）
     */
    private Boolean guideAddIdentity() throws SerException {
        Boolean flag = false;
        String userToken = RpcTransmit.getUserToken();
        UserBO userBO = userAPI.currentUser();
        RpcTransmit.transmitUserToken(userToken);
        String userName = userBO.getUsername();
        if (!"admin".equals(userName.toLowerCase())) {
            flag = cusPermissionSer.getCusPermission("1");
        } else {
            flag = true;
        }
        return flag;
    }


}