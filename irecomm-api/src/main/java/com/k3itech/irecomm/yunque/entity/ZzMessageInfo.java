package com.k3itech.irecomm.yunque.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 消息
 * </p>
 *
 * @author jobob
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ZZ_MESSAGE_INFO")
public class ZzMessageInfo extends Model<ZzMessageInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId("MSG_ID")
    private String msgId;

    /**
     * 发送人id
     */
    @TableField("SENDER")
    private String sender;

    /**
     * 接收人id
     */
    @TableField("RECEIVER")
    private String receiver;

    /**
     * 发送时间
     */
    @TableField("CREATETIME")
    private Date createtime;

    /**
     * 消息密级
     */
    @TableField("LEVELS")
    private String levels;

    /**
     * 消息内容(废弃)
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 消息类型（USER私人 GROUP群 MEET会议）
     */
    @TableField("TYPE")
    private String type;

    /**
     * 字段暂时废弃不用
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 字段暂时废弃不用
     */
    @TableField("ORG_NAME")
    private String orgName;

    @TableField("SENDER_SN")
    private String senderSn;

    /**
     * 跨场所类型0科室内 1跨科室 2跨场所
     */
    @TableField("ISCROSS")
    private String iscross;

    /**
     * 发送人IP
     */
    @TableField("IP")
    private String ip;

    /**
     * 是否附件类型1非附件2图片3附件
     */
    @TableField("FILE_TYPE")
    private String fileType;

    /**
     * 消息内容(文件名称)
     */
    @TableField("MSG")
    private String msg;

    /**
     * 如果是附件，附件ID
     */
    @TableField("FILE_ID")
    private String fileId;

    /**
     * 前端消息ID
     */
    @TableField("FRONT_ID")
    private String frontId;

    @TableField("SENDER_NAME")
    private String senderName;

    @TableField("RECEIVER_NAME")
    private String receiverName;

    @TableField("SENDER_AVATAR")
    private String senderAvatar;

    @TableField("RECEIVER_AVATAR")
    private String receiverAvatar;

    @TableField("SENDER_LEVELS")
    private String senderLevels;

    @TableField("CANCEL")
    private String cancel;

    @TableField("CANCEL_TIME")
    private Date cancelTime;

    /**
     * 数据抽取时间
     */
    @TableField("LAST_TIME")
    private Timestamp lastTime;

    /**
     * 删除标志
     */
    @TableField("ISDEL")
    private BigDecimal isdel;


    @Override
    protected Serializable pkVal() {
        return this.msgId;
    }

}
