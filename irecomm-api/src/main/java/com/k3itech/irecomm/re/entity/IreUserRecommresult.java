package com.k3itech.irecomm.re.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.KeySequence;
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
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@KeySequence(value = "SEQ_RECOMMRESULT")
@TableName("IRE_USER_RECOMMRESULT")
public class IreUserRecommresult extends Model<IreUserRecommresult> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private BigDecimal id;

    /**
     * 用户证件号
     */
    @TableField("ID_NUM")
    private String idNum;

    /**
     * 知识
     */
    @TableField("KNOWLEDGE")
    private String knowledge;

    /**
     * 用户喜好程度，0喜欢，1收藏，2点击
     */
    @TableField("ISLIKE")
    private String islike;

    @TableField("UPDATE_TIME")
    private Timestamp updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
