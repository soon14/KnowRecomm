package com.k3itech.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author:dell
 * @since: 2021-05-26
 */
@Data
public class RecommContent {

    /**
     * 标题
     */
    private String title;
    /**
     * 文档连接地址
     */
    private String url;
    /**
     * 文档产生日期
     */
    private Date time;
    /**
     * 内容来源（0知识，1PDM，2TDM，3其他）
     */
    private String source;
    /**
     * 作者
     */
    private String author="云雀";
    /**
     * 领域
     */
    private String domain="航空航天";
    /**
     * 推荐源
     */
    private String rsource;
    /**
     * 相关度
     */
    private Double relevancy;
    /**
     *
     */
    private String callback;
    /**
     * 备注
     */
    private String bz="暂无可显示摘要";


}
