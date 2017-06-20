package com.bjike.goddess.capability.service;

import com.bjike.goddess.capability.bo.CooperCapabilityBO;
import com.bjike.goddess.capability.enums.GuideAddrStatus;
import com.bjike.goddess.capability.excele.CooperCapabilityExcel;
import com.bjike.goddess.capability.to.CooperCapabilityTO;
import com.bjike.goddess.capability.to.GuidePermissionTO;
import com.bjike.goddess.common.api.dto.Restrict;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.jpa.service.ServiceImpl;
import com.bjike.goddess.capability.dto.CooperCapabilityDTO;
import com.bjike.goddess.capability.entity.CooperCapability;
import com.bjike.goddess.common.provider.utils.RpcTransmit;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.common.utils.excel.Excel;
import com.bjike.goddess.common.utils.excel.ExcelUtil;
import com.bjike.goddess.user.api.UserAPI;
import com.bjike.goddess.user.bo.UserBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合作对象商务展示业务实现
 *
 * @Author: [ tanghaixiang ]
 * @Date: [ 2017-03-23 04:31 ]
 * @Description: [ 合作对象商务展示业务实现 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@CacheConfig(cacheNames = "capabilitySerCache")
@Service
public class CooperCapabilitySerImpl extends ServiceImpl<CooperCapability, CooperCapabilityDTO> implements CooperCapabilitySer {

    @Autowired
    private CusPermissionSer cusPermissionSer ;
    @Autowired
    private ManageAuthenSer manageAuthenSer ;
    @Autowired
    private ProfessionAuthenSer professionAuthenSer ;
    @Autowired
    private CompanyProjectSer companyProjectSer ;
    @Autowired
    private UserAPI userAPI;


    @Override
    public Long counts(CooperCapabilityDTO cooperCapabilityDTO) throws SerException {
        if (StringUtils.isNotBlank(cooperCapabilityDTO.getCompanyName() )) {
            cooperCapabilityDTO.getConditions().add(Restrict.like("companyName",cooperCapabilityDTO.getCompanyName()));
        }
        Long count = super.count(cooperCapabilityDTO);
        return count;
    }

    @Override
    public CooperCapabilityBO getOne(String id) throws SerException {


        if(StringUtils.isBlank(id)){
            throw new SerException("id不能为空哦");
        }
        CooperCapability selfCapability = super.findById(id);
        return BeanTransform.copyProperties(selfCapability,CooperCapabilityBO.class);
    }

    @Override
    public List<CooperCapabilityBO> listCooperCapability(CooperCapabilityDTO cooperCapabilityDTO) throws SerException {

        Boolean permissionLevel = cusPermissionSer.getCusPermission("1");
        if ( !permissionLevel) {
            throw new SerException("您的帐号没有权限");
        }

        cooperCapabilityDTO.getSorts().add("createTime=desc");
        if (StringUtils.isNotBlank(cooperCapabilityDTO.getCompanyName() )) {
            cooperCapabilityDTO.getConditions().add(Restrict.like("companyName",cooperCapabilityDTO.getCompanyName()));
        }
        List<CooperCapability> list = super.findByCis(cooperCapabilityDTO, true);

        return BeanTransform.copyProperties(list, CooperCapabilityBO.class );
    }

    @Transactional(rollbackFor = SerException.class)
    @Override
    public CooperCapabilityBO addCooperCapability(CooperCapabilityTO cooperCapabilityTO) throws SerException {
        //商务模块添加权限
        Boolean permissionLevel = cusPermissionSer.getCusPermission("1");
        if ( !permissionLevel) {
            throw new SerException("您不是相应的人员，不可以进行添加合作对象操作");
        }

        CooperCapability cooperCapability = BeanTransform.copyProperties(cooperCapabilityTO,CooperCapability.class,true);
        cooperCapability.setCreateTime(LocalDateTime.now());

        CooperCapabilityDTO dto = new CooperCapabilityDTO();
        dto.getConditions().add(Restrict.eq("companyName",cooperCapabilityTO.getCompanyName().trim()) );
        List<CooperCapability> cooperCapabilityList = super.findByCis( dto );
        if( cooperCapabilityList != null && cooperCapabilityList.size() > 0 ){
            CooperCapability cooper = cooperCapabilityList.get(0);
            if(  cooper.getCompanyName().equals( cooperCapabilityTO.getCompanyName().trim() )
                    && (! cooper.getProfessionAuthen().equals( cooperCapabilityTO.getProfessionAuthen().trim() )
                    || ! cooper.getManageAuthen().equals( cooperCapabilityTO.getManageAuthen().trim() )
                    || ! cooper.getCompanyCertificate().equals( cooperCapabilityTO.getCompanyCertificate().trim() )
                    || ! cooper.getCompanyProject().equals( cooperCapabilityTO.getCompanyProject().trim() )
                    || ! cooper.getCompletePro().equals( cooperCapabilityTO.getCompletePro() )) ){

                throw  new SerException("公司名不能相同");
            }else{
                super.save( cooperCapability );

                //添加管理资质
                String[] managerAuths = cooperCapabilityTO.getManageAuthens();
                manageAuthenSer.addManageAuthen(managerAuths, cooperCapability.getId());

                //添加专业资质认证
                String[] professionAuths = cooperCapabilityTO.getProfessionAuthens();
                professionAuthenSer.addProfessionAuthen(professionAuths, cooperCapability.getId());

                //添加公司参与项目
                String[] companyProjects = cooperCapabilityTO.getCompanyProjects();
                companyProjectSer.addCompanyProject(companyProjects, cooperCapability.getId());

            }
        }else{
            super.save( cooperCapability );
        }

        return BeanTransform.copyProperties(cooperCapability, CooperCapabilityBO.class);
    }

    @Transactional(rollbackFor = SerException.class)
    @Override
    public CooperCapabilityBO editCooperCapability(CooperCapabilityTO cooperCapabilityTO) throws SerException {

        //商务模块编辑权限
        Boolean permissionLevel = cusPermissionSer.getCusPermission("1");
        if ( !permissionLevel) {
            throw new SerException("您不是相应的人员，不可以进行编辑合作对象操作");
        }

        CooperCapability cooperCapability = super.findById( cooperCapabilityTO.getId() );

        CooperCapabilityDTO dto = new CooperCapabilityDTO();
        dto.getConditions().add(Restrict.eq("companyName",cooperCapability.getCompanyName().trim()) );
        List<CooperCapability> cooperCapabilityList = super.findByCis( dto );
        cooperCapabilityList.stream().forEach(str -> {
            str.setCompanyName( cooperCapabilityTO.getCompanyName() );
            str.setProfessionAuthen( cooperCapabilityTO.getProfessionAuthen() );
            str.setManageAuthen( cooperCapabilityTO.getManageAuthen() );
            str.setCompanyCertificate( cooperCapabilityTO.getCompanyCertificate() );
            str.setCompanyProject( cooperCapabilityTO.getCompanyProject() );
            str.setCompletePro( cooperCapabilityTO.getCompletePro() );
            str.setModifyTime(LocalDateTime.now());
        });
        super.update( cooperCapabilityList );

        //编辑管理资质
        String[] managerAuths = cooperCapabilityTO.getManageAuthens();
        manageAuthenSer.editManageAuthen(managerAuths, cooperCapability.getId());

        //编辑专业资质认证
        String[] professionAuths = cooperCapabilityTO.getProfessionAuthens();
        professionAuthenSer.editProfessionAuthen(professionAuths, cooperCapability.getId());

        //编辑公司参与项目
        String[] companyProjects = cooperCapabilityTO.getCompanyProjects();
        companyProjectSer.editCompanyProject(companyProjects, cooperCapability.getId());

        return BeanTransform.copyProperties(cooperCapability, CooperCapabilityBO.class);
    }

    @Transactional(rollbackFor = SerException.class)
    @Override
    public void deleteCooperCapability(String id) throws SerException {
        //商务模块编辑权限
        Boolean permissionLevel = cusPermissionSer.getCusPermission("1");
        if ( !permissionLevel) {
            throw new SerException("您不是相应的人员，不可以进行删除合作对象操作");
        }

        super.remove(id);

        //删除管理资质
        manageAuthenSer.deleteManageAuthen(id);

        //删除专业资质认证
        professionAuthenSer.deleteProfessionAuthen(id);


        //删除公司参与项目数
        companyProjectSer.deleteCompanyProject(id);

    }

    
    @Override
    public CooperCapabilityBO getCompanyConnector(String id) throws SerException {
        CooperCapability cooperCapability = super.findById( id );
        return BeanTransform.copyProperties(cooperCapability, CooperCapabilityBO.class);
    }


    @Transactional(rollbackFor = SerException.class)
    @Override
    public CooperCapabilityBO editCompanyConnector(CooperCapabilityTO cooperCapabilityTO) throws SerException {
        CooperCapability cooperCapability = super.findById( cooperCapabilityTO.getId() );

        cooperCapability.setContactName( cooperCapabilityTO.getContactName());
        cooperCapability.setSex( cooperCapabilityTO.getSex());
        cooperCapability.setContactWay( cooperCapabilityTO.getContactWay());
        cooperCapability.setEmailName( cooperCapabilityTO.getEmailName());
        cooperCapability.setQqOrWechat( cooperCapabilityTO.getQqOrWechat());
        cooperCapability.setNatives( cooperCapabilityTO.getNatives());
        cooperCapability.setHobby( cooperCapabilityTO.getHobby());
        cooperCapability.setCharact( cooperCapabilityTO.getCharact());
        cooperCapability.setFamily( cooperCapabilityTO.getFamily());
        cooperCapability.setFamilyRelation( cooperCapabilityTO.getFamilyRelation());
        cooperCapability.setStudyExperience( cooperCapabilityTO.getStudyExperience());
        cooperCapability.setConnectExperience( cooperCapabilityTO.getConnectExperience());
        cooperCapability.setOldWorkPlace( cooperCapabilityTO.getOldWorkPlace());
        cooperCapability.setLivePlace( cooperCapabilityTO.getLivePlace());
        cooperCapability.setGrowthPlace( cooperCapabilityTO.getGrowthPlace());
        super.update( cooperCapability );
        return BeanTransform.copyProperties(cooperCapability, CooperCapabilityBO.class);
    }

    
    @Override
    public List<CooperCapabilityBO> listCooperCapabilityByName(CooperCapabilityDTO cooperCapabilityDTO) throws SerException {
        List<CooperCapabilityBO> cooperCapabilityBOS = new ArrayList<>();
        if(StringUtils.isNotBlank(cooperCapabilityDTO.getCompanyName()) ){
            cooperCapabilityDTO.getConditions().add( Restrict.eq("companyName",cooperCapabilityDTO.getCompanyName().trim()) );
            List<CooperCapability> cooperCapabilityList =  super.findByPage( cooperCapabilityDTO );
            cooperCapabilityBOS = BeanTransform.copyProperties( cooperCapabilityList ,CooperCapabilityBO.class );
        }
        return cooperCapabilityBOS;
    }

    
    @Override
    public CooperCapabilityBO getCompany(String companyName) throws SerException {
        CooperCapabilityBO cooperCapabilityBO = new CooperCapabilityBO();

        String[] fields = new String[]{"companyName","professionAuthen","manageAuthen","companyCertificate","companyProject","completePro"};
        List<CooperCapabilityBO> cooperBOS =super.findBySql("select companyName ,professionAuthen,manageAuthen,companyCertificate, companyProject , " +
                " completePro from capability_coopercapability where companyName = '"+companyName.trim()+"' " , CooperCapabilityBO.class, fields);

        if( cooperBOS != null && cooperBOS.size() >0 ){
            cooperCapabilityBO = cooperBOS.get(0);
        }

        return cooperCapabilityBO;
    }

    
    @Override
    public List<String> listAllCompanyName() throws SerException {
        String[] fields = new String[]{"companyName"};
        List<CooperCapabilityBO> cooperBOS =super.findBySql("select companyName  from capability_coopercapability group by companyName " , CooperCapabilityBO.class, fields);

        List<String> name = cooperBOS.stream().map(CooperCapabilityBO::getCompanyName).collect(Collectors.toList());
        return name;
    }

    @Override
    public List<CooperCapabilityBO> listCompanyContact(String companyName) throws SerException {
        String[] fields = new String[]{"companyName","contactName","contactWay"};
        List<CooperCapabilityBO> cooperBOS =super.findBySql("select companyName,contactName,contactWay from capability_coopercapability where  companyName ='"+companyName+"'" , CooperCapabilityBO.class, fields);

        return BeanTransform.copyProperties( cooperBOS , CooperCapabilityBO.class );
    }

    @Override
    public byte[] exportExcel(String companyName) throws SerException {
        CooperCapabilityDTO dto= new CooperCapabilityDTO();
        if (!org.springframework.util.StringUtils.isEmpty(companyName)) {
            dto.getConditions().add(Restrict.eq("companyName", companyName));
        }
        List<CooperCapability> list = super.findByCis(dto);
        List<CooperCapabilityExcel> toList = new ArrayList<CooperCapabilityExcel>();
        for (CooperCapability model : list) {
            CooperCapabilityExcel excel = new CooperCapabilityExcel();
            BeanUtils.copyProperties(model, excel);
            toList.add(excel);
        }
        Excel excel = new Excel(0, 2);
        byte[] bytes = ExcelUtil.clazzToExcel(toList, excel);
        return bytes;
    }

    @Override
    public Boolean sonPermission() throws SerException {
        String userToken = RpcTransmit.getUserToken();
        Boolean flagSee = guideSeeIdentity();
        if (flagSee) {
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
                flag = guideSeeIdentity();
                break;
            case EDIT:
                flag = guideSeeIdentity();
                break;
            case AUDIT:
                flag = guideSeeIdentity();
                break;
            case DELETE:
                flag = guideSeeIdentity();
                break;
            case CONGEL:
                flag = guideSeeIdentity();
                break;
            case THAW:
                flag = guideSeeIdentity();
                break;
            case COLLECT:
                flag = guideSeeIdentity();
                break;
            case IMPORT:
                flag = guideSeeIdentity();
                break;
            case EXPORT:
                flag = guideSeeIdentity();
                break;
            case UPLOAD:
                flag = guideSeeIdentity();
                break;
            case DOWNLOAD:
                flag = guideSeeIdentity();
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
            flag = cusPermissionSer.getCusPermission("1");
        } else {
            flag = true;
        }
        return flag;
    }
}