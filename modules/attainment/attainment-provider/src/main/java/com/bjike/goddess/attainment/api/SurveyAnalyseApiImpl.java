package com.bjike.goddess.attainment.api;

import com.bjike.goddess.attainment.bo.SurveyAnalyseBO;
import com.bjike.goddess.attainment.service.SurveyAnalyseSer;
import com.bjike.goddess.attainment.to.SurveyAnalyseTO;
import com.bjike.goddess.common.api.exception.SerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调研分析业务接口实现
 *
 * @Author: [ dengjunren ]
 * @Date: [ 2017-04-06 11:50 ]
 * @Description: [ 调研分析业务接口实现 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@Service("surveyAnalyseApiImpl")
public class SurveyAnalyseApiImpl implements SurveyAnalyseAPI {

    @Autowired
    private SurveyAnalyseSer surveyAnalyseSer;

    @Override
    public SurveyAnalyseBO save(SurveyAnalyseTO to) throws SerException {
        return surveyAnalyseSer.save(to);
    }

    @Override
    public SurveyAnalyseBO update(SurveyAnalyseTO to) throws SerException {
        return surveyAnalyseSer.update(to);
    }

    @Override
    public SurveyAnalyseBO delete(String id) throws SerException {
        return surveyAnalyseSer.delete(id);
    }

    @Override
    public List<SurveyAnalyseBO> findByPlan(String plan_id) throws SerException {
        return surveyAnalyseSer.findByPlan(plan_id);
    }
}