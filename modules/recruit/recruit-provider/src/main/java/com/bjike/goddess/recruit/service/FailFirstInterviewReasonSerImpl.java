package com.bjike.goddess.recruit.service;

import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.jpa.service.ServiceImpl;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.recruit.bo.FailFirstInterviewReasonBO;
import com.bjike.goddess.recruit.dto.FailFirstInterviewReasonDTO;
import com.bjike.goddess.recruit.entity.FailFirstInterviewReason;
import com.bjike.goddess.recruit.to.FailFirstInterviewReasonTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 未应约初试原因
 *
 * @Author: [ sunfengtao ]
 * @Date: [ 2017-04-08 05:10 ]
 * @Description: [  ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@Service
public class FailFirstInterviewReasonSerImpl extends ServiceImpl<FailFirstInterviewReason, FailFirstInterviewReasonDTO> implements FailFirstInterviewReasonSer {

    /**
     * 分页查询未应约初试原因
     *
     * @param dto
     * @return
     * @throws SerException
     */
    @Override
    public List<FailFirstInterviewReasonBO> list(FailFirstInterviewReasonDTO dto) throws SerException {
        List<FailFirstInterviewReason> list = super.findByPage(dto);
        List<FailFirstInterviewReasonBO> listBO = BeanTransform.copyProperties(list, FailFirstInterviewReasonBO.class);
        return listBO;
    }

    /**
     * 保存未应约初试原因
     *
     * @param to
     * @return
     * @throws SerException
     */
    @Override
    @Transactional(rollbackFor = SerException.class)
    public FailFirstInterviewReasonBO save(FailFirstInterviewReasonTO to) throws SerException {
        FailFirstInterviewReason model = BeanTransform.copyProperties(to, FailFirstInterviewReason.class, true);
        model = super.save(model);
        FailFirstInterviewReasonBO bo = BeanTransform.copyProperties(model, FailFirstInterviewReasonBO.class);
        return bo;
    }

    /**
     * 更新未应约初试原因
     *
     * @param to 未应约初试原因to
     * @throws SerException
     */
    @Override
    @Transactional(rollbackFor = SerException.class)
    public void update(FailFirstInterviewReasonTO to) throws SerException {
        if (StringUtils.isNotEmpty(to.getId())) {
            FailFirstInterviewReason model = super.findById(to.getId());
            if (model != null) {
                updateFailFirstInterviewReason(to, model);
            } else {
                throw new SerException("更新对象不能为空!");
            }
        } else {
            throw new SerException("更新ID不能为空!");
        }
    }

    /**
     * 更新未应约初试原因
     *
     * @param to 未应约初试原因to
     * @param model 未应约初试原因实体
     */
    private void updateFailFirstInterviewReason(FailFirstInterviewReasonTO to, FailFirstInterviewReason model) throws SerException {
        BeanTransform.copyProperties(to, model, true);
        model.setModifyTime(LocalDateTime.now());
        super.update(model);
    }

    /**
     * 删除未应约初试原因
     *
     * @param entity
     * @throws SerException
     */
    @Override
    @Transactional(rollbackFor = SerException.class)
    public void remove(FailFirstInterviewReason entity) throws SerException {
        super.remove(entity);
    }
}