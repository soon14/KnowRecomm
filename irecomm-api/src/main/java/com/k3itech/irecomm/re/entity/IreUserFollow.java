package com.k3itech.irecomm.re.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息标签
 * </p>
 *
 * @author jobob
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_USER_FOLLOW")
public class IreUserFollow extends Model<IreUserFollow> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户证件号
     */
    @TableId("ID_NUM")
    private String idNum;

    /**
     * 姓名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 岗位
     */
    @TableField("POST")
    private String post;

    /**
     * 职务
     */
    @TableField("USER_JOB")
    private String userJob;

    /**
     * 最近关注的型号
     */
    @TableField("FOLLOW_MODEL")
    private String followModel;

    /**
     * 最近关注的设备分系统
     */
    @TableField("FOLLOW_DEVICE")
    private String followDevice;

    /**
     * 关注的专业关键词
     */
    @TableField("FOLLOW_PRO")
    private String followPro;

    /**
     * 密级
     */
    @TableField("SECRET_LEVEL")
    private String secretLevel;

    /**
     * 组织
     */
    @TableField("ORG_CODE")
    private String orgCode;

    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 更新时间
     */
    @TableField("LAST_TIME")
    private Timestamp lastTime;


    @Override
    protected Serializable pkVal() {
        return this.idNum;
    }

}
