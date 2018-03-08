package com.bjike.goddess.reportmanagement.service;

import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.jpa.service.ServiceImpl;
import com.bjike.goddess.common.provider.utils.RpcTransmit;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.common.utils.date.DateUtil;
import com.bjike.goddess.reportmanagement.bo.*;
import com.bjike.goddess.reportmanagement.dto.AssetDTO;
import com.bjike.goddess.reportmanagement.dto.DebtDTO;
import com.bjike.goddess.reportmanagement.dto.DebtStructureAdviceDTO;
import com.bjike.goddess.reportmanagement.dto.FormulaDTO;
import com.bjike.goddess.reportmanagement.entity.Debt;
import com.bjike.goddess.reportmanagement.enums.DebtType;
import com.bjike.goddess.reportmanagement.enums.Form;
import com.bjike.goddess.reportmanagement.enums.GuideAddrStatus;
import com.bjike.goddess.reportmanagement.enums.Type;
import com.bjike.goddess.reportmanagement.to.DebtTO;
import com.bjike.goddess.reportmanagement.to.GuidePermissionTO;
import com.bjike.goddess.reportmanagement.utils.Static;
import com.bjike.goddess.user.api.UserAPI;
import com.bjike.goddess.user.bo.UserBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 负债表业务实现
 *
 * @Author: [ chenjunhao ]
 * @Date: [ 2017-06-19 11:21 ]
 * @Description: [ 负债表业务实现 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@CacheConfig(cacheNames = "reportmanagementSerCache")
@Service
public class DebtSerImpl extends ServiceImpl<Debt, DebtDTO> implements DebtSer {
    @Autowired
    private FormulaSer formulaSer;
    @Autowired
    private DebtStructureAdviceSer debtStructureAdviceSer;
    @Autowired
    private CusPermissionSer cusPermissionSer;
    @Autowired
    private UserAPI userAPI;
    @Autowired
    private AssetSer assetSer;


    /**
     * 核对查看权限（部门级别）
     */
    private void checkSeeIdentity() throws SerException {
        Boolean flag = false;
        String userToken = RpcTransmit.getUserToken();
        UserBO userBO = userAPI.currentUser();
        RpcTransmit.transmitUserToken(userToken);
        String userName = userBO.getUsername();
        if (!"admin".equals(userName.toLowerCase())) {
            flag = cusPermissionSer.getCusPermission("1");
            if (!flag) {
                throw new SerException("您不是相应部门的人员，不可以操作");
            }
        }
        RpcTransmit.transmitUserToken(userToken);
    }

    /**
     * 核对添加修改删除审核权限（岗位级别）
     */
    private void checkAddIdentity() throws SerException {
        Boolean flag = false;
        String userToken = RpcTransmit.getUserToken();
        UserBO userBO = userAPI.currentUser();
        RpcTransmit.transmitUserToken(userToken);
        String userName = userBO.getUsername();
        if (!"admin".equals(userName.toLowerCase())) {
            flag = cusPermissionSer.busCusPermission("2");
            if (!flag) {
                throw new SerException("您不是相应部门的人员，不可以操作");
            }
        }
        RpcTransmit.transmitUserToken(userToken);
    }

    /**
     * 核对查看权限（部门级别）
     */
    private Boolean guideSeeIdentity() throws SerException {
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

    /**
     * 核对添加修改删除审核权限（岗位级别）
     */
    private Boolean guideAddIdentity() throws SerException {
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

    @Override
    public Boolean sonPermission() throws SerException {
        String userToken = RpcTransmit.getUserToken();
        Boolean flagSee = guideSeeIdentity();
        RpcTransmit.transmitUserToken(userToken);
        Boolean flagAdd = guideAddIdentity();
        if (flagSee || flagAdd) {
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
            case DELETE:
                flag = guideAddIdentity();
                break;
            case CONGEL:
                flag = guideAddIdentity();
                break;
            case THAW:
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

        RpcTransmit.transmitUserToken(userToken);
        return flag;
    }

    @Override
    public DebtBO save(DebtTO to) throws SerException {
        checkAddIdentity();
        Debt entity = BeanTransform.copyProperties(to, Debt.class, true);
        super.save(entity);
        return BeanTransform.copyProperties(entity, DebtBO.class);
    }

//    @Override
//    public DebtBO find(String id, String startTime, String endTime) throws SerException {
//        Debt entity = super.findById(id);
//        if (entity == null) {
//            throw new SerException("该对象不存在");
//        }
//        DebtBO bo = BeanTransform.copyProperties(entity, DebtBO.class);
//        bo.setStartTime(startTime);
//        bo.setEndTime(endTime);
//        return bo;
//    }

    @Override
    public List<DebtBO> list(DebtDTO dto) throws SerException {
//        checkSeeIdentity();
        if (StringUtils.isBlank(dto.getStartTime()) && StringUtils.isBlank(dto.getEndTime())) {
            dto.setStartTime(DateUtil.dateToString(DateUtil.getStartMonth()));
            dto.setEndTime(DateUtil.dateToString(DateUtil.getEndMonth()));
        }
        FormulaDTO formulaDTO = new FormulaDTO();
        BeanUtils.copyProperties(dto, formulaDTO);
        dto.getSorts().add("debtType=ASC");
        dto.getSorts().add("createTime=ASC");
        List<Debt> list = super.findByCis(dto);
        List<DebtBO> boList = new ArrayList<DebtBO>();
        boolean b1 = true;
        boolean b2 = true;
        boolean b3 = true;
        boolean b4 = true;
        double beginSum = 0;
        double endSum = 0;
        double debtBegin = 0;
        double debtEnd = 0;    //负债合计
        double countBegin = 0;
        double countEnd = 0;       //负债和所有权益合计
        int num = Static.getNum();
        for (Debt debt : list) {
            List<FormulaBO> formulaBOs = formulaSer.findByFid(debt.getId(), formulaDTO);
            if (DebtType.AFLOW.equals(debt.getDebtType()) && b1) {
                DebtBO debtBO = new DebtBO();
                debtBO.setDebt("流动负债：");
                boList.add(debtBO);
                b1 = false;
            } else if (DebtType.BLONG.equals(debt.getDebtType()) && b2) {
                DebtBO sumBO = new DebtBO();
                sumBO.setDebt("流动负债合计");
                sumBO.setBeginDebt(beginSum);
                sumBO.setEndDebt(endSum);
                sumBO.setDebtNum(num);
                num++;
                boList.add(sumBO);
                debtBegin += beginSum;
                debtEnd += endSum;
                beginSum = 0;
                endSum = 0;    //置为0
                DebtBO debtBO = new DebtBO();
                debtBO.setDebt("长期负债：");
                boList.add(debtBO);
                b2 = false;
            } else if (DebtType.CTAX.equals(debt.getDebtType()) && b3) {
                DebtBO sumBO = new DebtBO();
                sumBO.setDebt("长期负债合计");
                sumBO.setBeginDebt(beginSum);
                sumBO.setEndDebt(endSum);
                sumBO.setDebtNum(num);
                num++;
                boList.add(sumBO);
                debtBegin += beginSum;
                debtEnd += endSum;
                beginSum = 0;
                endSum = 0;    //置为0
                DebtBO debtBO = new DebtBO();
                debtBO.setDebt("递延税项：");
                boList.add(debtBO);
                b3 = false;
            } else if (DebtType.DALL.equals(debt.getDebtType()) && b4) {
                DebtBO sumBO = new DebtBO();
                sumBO.setDebt("负债合计");
                debtBegin += beginSum;
                debtEnd += endSum;
                sumBO.setBeginDebt(debtBegin);
                sumBO.setEndDebt(debtEnd);
                sumBO.setDebtNum(num);
                num++;
                boList.add(sumBO);
                beginSum = 0;
                endSum = 0;    //置为0
                DebtBO debtBO1 = new DebtBO();
                boList.add(debtBO1);
                DebtBO debtBO = new DebtBO();
                debtBO.setDebt("所有者权益(或股东权益)：");
                boList.add(debtBO);
                b4 = false;
            }
            DebtBO bo = BeanTransform.copyProperties(debt, DebtBO.class);
            if (formulaBOs != null) {
                FormulaBO formulaBO = formulaBOs.get(formulaBOs.size() - 1);
                bo.setBeginDebt(formulaBO.getBegin());
                bo.setEndDebt(formulaBO.getEnd());
                /*if (Type.ADD.equals(debt.getType())) {
                    if (!"实收资本".equals(bo.getDebt()) || !"已归还投资".equals(bo.getDebt())) {
                        if ("实收资本（或股本）净额".equals(bo.getDebt()) || "资本公积".equals(bo.getDebt()) ||
                                "盈余公积".equals(bo.getDebt()) || "未分配利润".equals(bo.getDebt())) {
                            beginSum += bo.getBeginDebt();
                            endSum += bo.getEndDebt();
                        }

                        countBegin += bo.getBeginDebt();
                        countEnd += bo.getEndDebt();
                    }
                } else if (Type.REMOVE.equals(debt.getType())) {
                    if (!"实收资本".equals(bo.getDebt()) || !"已归还投资".equals(bo.getDebt())) {
                        if ("实收资本（或股本）净额".equals(bo.getDebt()) || "资本公积".equals(bo.getDebt()) ||
                                "盈余公积".equals(bo.getDebt()) || "未分配利润".equals(bo.getDebt())) {
                            beginSum -= bo.getBeginDebt();
                            endSum -= bo.getEndDebt();
                        }

                        countBegin -= bo.getBeginDebt();
                        countEnd -= bo.getEndDebt();
                    }
                }*/
                if ("实收资本（或股本）净额".equals(bo.getDebt()) || "实收资本(或股本)净额".equals(bo.getDebt()) || "资本公积".equals(bo.getDebt()) ||
                        "盈余公积".equals(bo.getDebt()) || "未分配利润".equals(bo.getDebt())) {
                    beginSum += bo.getBeginDebt();
                    endSum += bo.getEndDebt();
                }

               /* if ("负债合计".equals(bo.getDebt()) || "所有者权益(或股东权益)合计".equals(bo.getDebt()) ||
                        "所有者权益（或股东权益）合计".equals(bo.getDebt())) {
                    countBegin += bo.getBeginDebt();
                    countEnd += bo.getEndDebt();
                }*/
            }
            bo.setDebtNum(num);
            num++;
            boList.add(bo);

        }

        AssetDTO assetDTO = new AssetDTO();
        BeanUtils.copyProperties(dto, assetDTO, "sorts");
        Long size = assetSer.count(assetDTO);

//        if (size + 10 > boList.size() + 2) {
//            for (int i = 0; i < size + 10 - (boList.size() + 2); i++) {
//                DebtBO debtBO = new DebtBO();
//                boList.add(debtBO);
//            }
//        }

        DebtBO lastTwo = new DebtBO();
        lastTwo.setDebt("所有者权益(或股东权益)合计");
        lastTwo.setBeginDebt(beginSum);
        lastTwo.setEndDebt(endSum);
        lastTwo.setDebtNum(num);
        num++;
        boList.add(lastTwo);

        for (DebtBO bo : boList) {
            if ("负债合计".equals(bo.getDebt())) {
                countBegin += bo.getBeginDebt();
                countEnd += bo.getEndDebt();
                break;
            }
        }
        DebtBO lastBO = new DebtBO();
        lastBO.setDebt("负债和所有者权益(或股东权益)总计");
//        lastBO.setBeginDebt(countBegin);
//        lastBO.setEndDebt(countEnd);
        lastBO.setBeginDebt(beginSum + countBegin);
        lastBO.setEndDebt(endSum + countEnd);
        lastBO.setDebtNum(num);
        num++;
        boList.add(lastBO);
        boList.stream().forEach(obj -> {
            obj.setStartTime(dto.getStartTime());
            obj.setEndTime(dto.getEndTime());
        });
        return this.convertDebt(boList);
    }

    List<DebtBO> convertDebt(List<DebtBO> list) {
        List<DebtBO> bos = new ArrayList<>();
        DebtBO bo1 = new DebtBO();
        DebtBO bo2 = new DebtBO();
        DebtBO bo3 = new DebtBO();
        DebtBO bo4 = new DebtBO();
        DebtBO bo5 = new DebtBO();
        DebtBO bo6 = new DebtBO();
        DebtBO bo7 = new DebtBO();
        DebtBO bo8 = new DebtBO();
        DebtBO bo9 = new DebtBO();
        DebtBO bo10 = new DebtBO();
        DebtBO bo11 = new DebtBO();
        DebtBO bo12 = new DebtBO();
        DebtBO bo13 = new DebtBO();
        DebtBO bo14 = new DebtBO();
        DebtBO bo15 = new DebtBO();
        DebtBO bo16 = new DebtBO();
        DebtBO bo17 = new DebtBO();
        DebtBO bo18 = new DebtBO();
        DebtBO bo19 = new DebtBO();
        DebtBO bo20 = new DebtBO();
        DebtBO bo21 = new DebtBO();
        DebtBO bo22 = new DebtBO();
        DebtBO bo23 = new DebtBO();
        DebtBO bo24 = new DebtBO();
        DebtBO bo25 = new DebtBO();
        DebtBO bo26 = new DebtBO();
        DebtBO bo27 = new DebtBO();
        DebtBO bo28 = new DebtBO();
        DebtBO bo29 = new DebtBO();
        DebtBO bo30 = new DebtBO();
        DebtBO bo31 = new DebtBO();
        DebtBO bo32 = new DebtBO();
        DebtBO bo33 = new DebtBO();
        DebtBO bo34 = new DebtBO();
        DebtBO bo35 = new DebtBO();
        DebtBO bo36 = new DebtBO();
        DebtBO bo37 = new DebtBO();
        DebtBO bo38 = new DebtBO();

        for (DebtBO bo : list) {
            if ("流动负债：".equals(bo.getDebt()) || "流动负债:".equals(bo.getDebt())) {
                bo1 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("短期借款".equals(bo.getDebt())) {
                bo2 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("应付票据".equals(bo.getDebt())) {
                bo3 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("应付账款".equals(bo.getDebt())) {
                bo4 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("预收账款".equals(bo.getDebt())) {
                bo5 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("应付职工薪酬".equals(bo.getDebt())) {
                bo6 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("应付利息".equals(bo.getDebt())) {
                bo7 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("应付股利".equals(bo.getDebt())) {
                bo8 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("应交税金".equals(bo.getDebt())) {
                bo9 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("其他应交款".equals(bo.getDebt())) {
                bo10 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("其他应付款".equals(bo.getDebt())) {
                bo11 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("预提费用".equals(bo.getDebt())) {
                bo12 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("预计负债".equals(bo.getDebt())) {
                bo13 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("一年内到期的长期负债".equals(bo.getDebt())) {
                bo14 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("其他流动负债".equals(bo.getDebt())) {
                bo15 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
                bo16 = new DebtBO();
            }
            if ("流动负债合计".equals(bo.getDebt())) {
                bo17 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("长期负债：".equals(bo.getDebt()) || "长期负债:".equals(bo.getDebt())) {
                bo18 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("长期借款".equals(bo.getDebt())) {
                bo19 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("应付债券".equals(bo.getDebt())) {
                bo20 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("长期应付款".equals(bo.getDebt())) {
                bo21 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("专项应付款".equals(bo.getDebt())) {
                bo22 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("其他长期负债".equals(bo.getDebt())) {
                bo23 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("长期负债合计".equals(bo.getDebt())) {
                bo24 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("递延税项：".equals(bo.getDebt()) || "递延税项:".equals(bo.getDebt())) {
                bo25 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("递延税款贷项".equals(bo.getDebt())) {
                bo26 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("负债合计".equals(bo.getDebt())) {
                bo27 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
                bo28 = new DebtBO();
            }
            if ("所有者权益(或股东权益)：".equals(bo.getDebt()) || "所有者权益(或股东权益):".equals(bo.getDebt())
                    || "所有者权益（或股东权益）：".equals(bo.getDebt()) || "所有者权益（或股东权益）:".equals(bo.getDebt())) {
                bo29 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("实收资本(或股本)".equals(bo.getDebt()) || "实收资本（或股本）".equals(bo.getDebt())) {
                bo30 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("减：已归还投资".equals(bo.getDebt()) || "减:已归还投资".equals(bo.getDebt())) {
                bo31 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("实收资本(或股本)净额".equals(bo.getDebt()) || "实收资本（或股本）净额".equals(bo.getDebt())) {
                bo32 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("资本公积".equals(bo.getDebt())) {
                bo33 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("盈余公积".equals(bo.getDebt())) {
                bo34 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("其中：法定公益金".equals(bo.getDebt()) || "其中:法定公益金".equals(bo.getDebt())) {
                bo35 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("未分配利润".equals(bo.getDebt())) {
                bo36 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("所有者权益(或股东权益)合计".equals(bo.getDebt()) || "所有者权益（或股东权益）合计".equals(bo.getDebt())) {
                bo37 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
            if ("负债和所有者权益（或股东权益）总计".equals(bo.getDebt()) || "负债和所有者权益(或股东权益)总计".equals(bo.getDebt())) {
                bo38 = new DebtBO(bo.getStartTime(), bo.getEndTime(), bo.getDebt(), bo.getDebtType(), bo.getType(), bo.getDebtNum(), bo.getBeginDebt(), bo.getCurrent(), bo.getEndDebt());
            }
        }

        bos.add(bo1);
        bos.add(bo2);
        bos.add(bo3);
        bos.add(bo4);
        bos.add(bo5);
        bos.add(bo6);
        bos.add(bo7);
        bos.add(bo8);
        bos.add(bo9);
        bos.add(bo10);
        bos.add(bo11);
        bos.add(bo12);
        bos.add(bo13);
        bos.add(bo14);
        bos.add(bo15);
        bos.add(bo16);
        bos.add(bo17);
        bos.add(bo18);
        bos.add(bo19);
        bos.add(bo20);
        bos.add(bo21);
        bos.add(bo22);
        bos.add(bo23);
        bos.add(bo24);
        bos.add(bo25);
        bos.add(bo26);
        bos.add(bo27);
        bos.add(bo28);
        bos.add(bo29);
        bos.add(bo30);
        bos.add(bo31);
        bos.add(bo32);
        bos.add(bo33);
        bos.add(bo34);
        bos.add(bo35);
        bos.add(bo36);
        bos.add(bo37);
        bos.add(bo38);

        return bos;
    }

    @Override
    public List<StructureBO> debtStructure(DebtDTO dto) throws SerException {
        checkSeeIdentity();
        String userToken = RpcTransmit.getUserToken();
        FormulaDTO formulaDTO = new FormulaDTO();
        BeanUtils.copyProperties(dto, formulaDTO, "sorts");
        dto.getSorts().add("debtType=ASC");
        dto.getSorts().add("createTime=asc");
        List<Debt> list = super.findByCis(dto);
        List<StructureBO> boList = new ArrayList<StructureBO>();
        boolean b1 = true;
        boolean b2 = true;
        boolean b3 = true;
        double flowSum = 0;
        double longSum = 0;
        double allSum = 0;
        double currentSum = 0;
        double countCurrent = 0;       //负债和所有权益合计
        for (Debt debt : list) {
            List<FormulaBO> formulaBOs = formulaSer.findByFid(debt.getId(), formulaDTO);
            if (formulaBOs != null) {
                if (DebtType.BLONG.equals(debt.getDebtType()) && b1) {
                    flowSum = currentSum;
                    currentSum = 0;    //置为0
                    b1 = false;
                } else if (DebtType.CTAX.equals(debt.getDebtType()) && b2) {
                    longSum = currentSum;
                    currentSum = 0;    //置为0
                    b2 = false;
                } else if (DebtType.DALL.equals(debt.getDebtType()) && b3) {
                    currentSum = 0;    //置为0
                    b3 = false;
                }
                FormulaBO formulaBO = formulaBOs.get(formulaBOs.size() - 1);
                DebtBO bo = BeanTransform.copyProperties(debt, DebtBO.class);
//                资产期末数
//                bo.setCurrent(formulaBO.getCurrent());
                bo.setCurrent(formulaBO.getEnd());
                if (Type.ADD.equals(debt.getType())) {
                    currentSum += bo.getCurrent();
                    countCurrent += bo.getCurrent();
                } else if (Type.REMOVE.equals(debt.getType())) {
                    currentSum -= bo.getCurrent();
                    countCurrent -= bo.getCurrent();
                }
            }
        }
        allSum = currentSum;
        StructureBO flowBO = new StructureBO();
        flowBO.setProject("流动负债合计");
        flowBO.setFee(flowSum);
        String flow = "0";
        if (0d != countCurrent) {
            flow = String.format("%.2f", (flowSum / countCurrent) * 100);
        }
        flowBO.setScale(flow + "%");
        boList.add(flowBO);
        StructureBO longBO = new StructureBO();
        longBO.setProject("长期负债合计");
        longBO.setFee(longSum);
        String l = "0";
        if (0d != countCurrent) {
            l = String.format("%.2f", (longSum / countCurrent) * 100);
        }
        longBO.setScale(l + "%");
        boList.add(longBO);
        StructureBO debtBO = new StructureBO();
        debtBO.setProject("负债合计");
        debtBO.setFee(flowSum + longSum);
        String debt = "0";
        if (0d != countCurrent) {
            debt = String.format("%.2f", (debtBO.getFee() / countCurrent) * 100);
        }
        debtBO.setScale(debt + "%");
        boList.add(debtBO);
        StructureBO allBO = new StructureBO();
        allBO.setProject("所有者权益合计");
        allBO.setFee(allSum);
        String all = "0";
        if (0d != countCurrent) {
            all = String.format("%.2f", (allSum / countCurrent) * 100);
        }
        allBO.setScale(all + "%");
        boList.add(allBO);
        StructureBO sumBO = new StructureBO();
        sumBO.setProject("负债与所有者权益总计");
        sumBO.setFee(countCurrent);
        sumBO.setScale("100%");
        boList.add(sumBO);
        StructureBO rate = new StructureBO();
        rate.setFee(-1.00);
        rate.setScale("随便");
        rate.setProject("比例说明");
        rate.setBestScale("低负债资本、高权益资本可以降低企业财务风险，" +
                "减少企业发生债务危机的比率，但是会增加企业资本成本，不能有效发挥债务资本的财务杠杆效益。");
        boList.add(rate);
        RpcTransmit.transmitUserToken(userToken);
        String advice = debtStructureAdvice(flow, l, all);
        StructureBO adviceBO = new StructureBO();
        adviceBO.setProject("管理建议");
        adviceBO.setFee(-1.00);
        adviceBO.setScale("随便");
        adviceBO.setBestScale(advice);
        boList.add(adviceBO);
        return boList;
    }

    /**
     * 获取负债与权益结构管理建议
     *
     * @param flow
     * @param l
     * @param all
     * @return
     * @throws SerException
     */
    private String debtStructureAdvice(String flow, String l, String all) throws SerException {
        List<DebtStructureAdviceBO> advices = debtStructureAdviceSer.list(new DebtStructureAdviceDTO());
        String advice = null;
        if (advices != null && !advices.isEmpty()) {
            for (DebtStructureAdviceBO r : advices) {
                boolean b1 = Double.parseDouble(flow) >= r.getFlowMin() && Double.parseDouble(flow) <= r.getFlowMax();
                boolean b2 = Double.parseDouble(l) >= r.getLongMin() && Double.parseDouble(l) <= r.getLongMax();
                boolean b3 = Double.parseDouble(all) >= r.getAllMin() && Double.parseDouble(all) <= r.getAllMax();
                if (b1 && b2 && b3) {
                    advice = r.getAdvice();
                }
            }
        }
        return advice;
    }

    @Override
    public List<DetailBO> findDetails(String id, DebtDTO dto) throws SerException {
        checkSeeIdentity();
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        FormulaDTO formulaDTO = new FormulaDTO();
        BeanUtils.copyProperties(dto, formulaDTO);
        List<FormulaBO> list = formulaSer.findByFid(id, formulaDTO);
        List<DetailBO> boList = new ArrayList<>();
        if ((list != null) && (!list.isEmpty())) {
            FormulaBO last = list.get(list.size() - 1);
            double begin = last.getBegin();
            double current = last.getCurrent();

            Double beginningCreditAmount = last.getBeginningCreditAmount();//期初余额
            Double issueDebitAmount = last.getIssueDebitAmount();//本期借方总额
            Double issueCreditAmount = last.getIssueCreditAmount();//本期贷方总额
            Double issueTotalAmount = last.getIssueTotalAmount();//本期合计余额
            Double endDebitAmount = last.getEndDebitAmount();//期末借方总额
            Double endCreditAmount = last.getEndCreditAmount();//期末贷方总额
            Double endTotalAmount = last.getEndTotalAmount();//本年累计额
//            Form form = last.getForm();
            Form form = Form.CREDIT;
            double currentSum = 0;
            String project = findByID(id).getDebt();
            String term = startTime + "~" + endTime;
            DetailBO currentBO = new DetailBO();
            currentBO.setProject(project);
            currentBO.setTerm(term);
            currentBO.setState("本期合计");
            currentBO.setForm(form);
            currentBO.setDebit(issueDebitAmount);
            currentBO.setCredit(issueCreditAmount);
            currentBO.setRemain(issueTotalAmount);
            currentBO.setRemain(currentSum);
//            if (Form.DEBIT.equals(form)) {
//                currentSum = begin + current;
//                currentBO.setDebit(current);
//            } else if (Form.CREDIT.equals(form)) {
//                currentSum = begin - current;
//                currentBO.setCredit(current);
//            }
            boList.add(currentBO);

            double year = currentSum;
            DetailBO beginBO = new DetailBO();
            beginBO.setProject(project);
            beginBO.setTerm(term);
            beginBO.setState("期初余额");
            beginBO.setForm(form);
            beginBO.setRemain(beginningCreditAmount);
            boList.add(beginBO);

            DetailBO yearBO = new DetailBO();
            yearBO.setTerm(term);
            yearBO.setState("本年累计");
            yearBO.setForm(form);
            yearBO.setDebit(endDebitAmount);
            yearBO.setCredit(endCreditAmount);
            yearBO.setRemain(endTotalAmount);
//            yearBO.setRemain(year);
            boList.add(yearBO);
        }
        return boList;
    }

    @Override
    public DebtBO findByID(String id) throws SerException {
        Debt entity = super.findById(id);
        if (entity == null) {
            throw new SerException("该对象不存在");
        }
        return BeanTransform.copyProperties(entity, DebtBO.class);
    }

    @Override
    public Long count(DebtDTO dto) throws SerException {
        return super.count(dto);
    }

    @Override
    @Transactional(rollbackFor = SerException.class)
    public void edit(DebtTO to) throws SerException {
        checkAddIdentity();
        Debt entity = super.findById(to.getId());
        if (entity == null) {
            throw new SerException("该对象不存在");
        }
        LocalDateTime a = entity.getCreateTime();
        entity = BeanTransform.copyProperties(to, Debt.class, true);
        entity.setCreateTime(a);
        entity.setModifyTime(LocalDateTime.now());
        super.update(entity);
    }

    @Override
    @Transactional(rollbackFor = SerException.class)
    public void delete(String id) throws SerException {
        checkAddIdentity();
        Debt entity = super.findById(id);
        if (entity == null) {
            throw new SerException("该对象不存在");
        }
        super.remove(id);
    }

    @Override
    public List<DebtBO> list1(DebtDTO dto) throws SerException {
        checkSeeIdentity();
        List<Debt> list = super.findByCis(dto, true);
        return BeanTransform.copyProperties(list, DebtBO.class);
    }
}