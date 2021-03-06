package com.bjike.goddess.recruit.vo;

import com.bjike.goddess.common.api.bo.BaseBO;

/**
 * 未应约初试原因
 *
 * @Author: [sunfengtao]
 * @Date: [2017-03-10 17:08]
 * @Description: [ ]
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
public class FailFirstInterviewReasonVO {

    /**
     * id
     */
    private String id;

    /**
     * 未应约初试原因类型
     */
    private String failFirstInterviewReasonType;

    /**
     * 备注
     */
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFailFirstInterviewReasonType() {
        return failFirstInterviewReasonType;
    }

    public void setFailFirstInterviewReasonType(String failFirstInterviewReasonType) {
        this.failFirstInterviewReasonType = failFirstInterviewReasonType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
