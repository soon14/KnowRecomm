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
 * @since 2021-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@KeySequence(value = "SEQ_RECOMMLOG")
@TableName("IRE_RECOMM_LOG")
public class IreRecommLog extends Model<IreRecommLog> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private BigDecimal id;

    /**
     * 设分支
     */
    @TableField("ID_NUM")
    private String idNum;

    /**
     * 知识ids
     */
    @TableField("KNOWLEDGE")
    private String knowledge;

    /**
     * 推送时间
     */
    @TableField("POST_TIME")
    private Timestamp postTime;

    @TableField("KTYPE")
    private String ktype;

    /**
     * 状态0成功，1失败
     */
    @TableField("STATUS")
    private String status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
