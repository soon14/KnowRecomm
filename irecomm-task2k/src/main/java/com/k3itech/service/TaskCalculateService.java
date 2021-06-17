package com.k3itech.service;

import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.vo.TaskParam;
import com.k3itech.vo.TaskRecommResults;


import java.util.List;

/**
 * @author:dell
 * @since: 2021-05-25
 */
public interface TaskCalculateService {
    /**
     * 用户知识匹配计算
     * @param taskParam
     * @param iKnowledgeInfos
     */
    public TaskRecommResults compareT2K(TaskParam taskParam, List<IreKnowledgeInfo> iKnowledgeInfos);
}
