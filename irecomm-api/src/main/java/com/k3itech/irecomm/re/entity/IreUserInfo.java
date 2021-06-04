package com.k3itech.irecomm.re.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author jobob
 * @since 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_USER_INFO")
public class IreUserInfo extends Model<IreUserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户证件号
     */
    @TableId("ID_NUM")
    private String idNum;

    /**
     * 用户姓名
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


    @Override
    protected Serializable pkVal() {
        return this.idNum;
    }

}
