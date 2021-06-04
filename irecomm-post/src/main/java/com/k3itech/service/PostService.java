package com.k3itech.service;


import com.k3itech.irecomm.re.entity.IreUserFollow;

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
}
