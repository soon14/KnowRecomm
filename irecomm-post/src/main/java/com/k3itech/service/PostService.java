package com.k3itech.service;


import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.vo.RecommContent;

import java.util.List;

/**
 * @author dell
 * @since 2021-05-16
 */
public interface PostService {
    /**
     * 知识调用
     * @param iUserFollow
     * @return
     */
    public boolean postKnowledge(IreUserFollow iUserFollow);
    public List<RecommContent> getKnowledge(IreUserFollow iUserFollow);
}
