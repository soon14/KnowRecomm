package com.k3itech.service;

import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreUserFollow;


import java.util.List;

/**
 * @author:dell
 * @since: 2021-05-25
 */
public interface CalculateService {
    /**
     * 用户知识匹配计算
     * @param iUserFollow
     * @param iKnowledgeInfos
     */
    public void compareU2K(IreUserFollow iUserFollow, List<IreKnowledgeInfo> iKnowledgeInfos);
}
