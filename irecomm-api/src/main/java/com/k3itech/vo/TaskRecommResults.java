package com.k3itech.vo;

import com.k3itech.irecomm.re.entity.IreUserFollow;
import lombok.Data;

import java.util.List;

@Data
public class TaskRecommResults {
    /**
     * 用户
     */
    private TaskParam taskParam;
    /**
     * 匹配结果
     */
    private List<RecommResult> recommResults;
    /**
     * 是否已推送
     */
    private Integer status;

    /**
     * 推送时间
     */
    private String postTime;
}