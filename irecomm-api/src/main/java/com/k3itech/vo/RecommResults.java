package com.k3itech.vo;

import com.k3itech.irecomm.re.entity.IreUserFollow;
import lombok.Data;

import java.util.List;

/**
 * @author:dell
 * @since: 2021-05-25
 */

@Data
public class RecommResults {
    /**
     * 用户
     */
    private IreUserFollow iUserFollow;
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
