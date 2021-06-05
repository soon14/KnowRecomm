package com.k3itech.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author:dell
 * @since: 2021-06-04
 */
@Data
public class YunqueContent {
    /**
     * 通知主键（研讨内部唯一id）
     * 1
     */
    private String noticeId="AvRqvvAY";
    /**
     *是否已读 （默认0未读，1已读，2已同意，3已驳回，4已阅）
     * 1
     */
    private Integer noticeRead=0;
    /**
     *通知人姓名
     * 1
     */
    private String senderName="推荐系统";
    /**
     *接收人身份证
     * 2
     */
    private String receiverId;
    /**
     *接收人姓名
     * 2
     */
    private String receiverName;
    /**
     *接收人所在部门（名称）
     * 2
     */
    private String senderOrgName;
    /**
     *通知来源（业务/应用名称）
     * 1
     */
    private String sourceName;
    /**
     *来源IP
     * 2
     */
    private String soureceIP;
    /**
     *801：审批；802：系统消息；803：数据更新；804：推荐
     * 1
     */
    private Integer senderType=804;
    /**
     *通知发送时间
     * 1
     */
    private Date time;
    /**
     *协议内容
     * 2
     */
    private Object msgContent;
    /**
     *紧急状态（0重要、1非重要）
     * 1
     */
    private Integer status;
    /**
     *回执（研讨内部使用，标注消息已阅，取消强提醒）
     * 2
     */
    private Integer receipt;
    /**
     *密级 1：非密，2秘密，3机密
     * 1
     */
    private Integer level=1;
    /**
     *备注
     * 2
     */
    private String bz;
}
