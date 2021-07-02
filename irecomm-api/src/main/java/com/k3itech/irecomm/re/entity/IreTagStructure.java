package com.k3itech.irecomm.re.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_TAG_STRUCTURE")
public class IreTagStructure extends Model<IreTagStructure> {

    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    @TableId("ID")
    private BigDecimal id;

    /**
     * 父标识
     */
    @TableField("PARENT_ID")
    private BigDecimal parentId;

    /**
     * 所有父标识
     */
    @TableField("PARENT_IDS")
    private String parentIds;

    /**
     * 名称
     */
    @TableField("TEXT")
    private String text;

    /**
     * 编码
     */
    @TableField("CODE")
    private String code;

    /**
     * 父编码
     */
    @TableField("PARENT_CODE")
    private String parentCode;

    /**
     * 所有父编码
     */
    @TableField("PARENT_CODES")
    private String parentCodes;

    /**
     * 是否叶子节点（0根节点，1为叶子节点）
     */
    @TableField("LEAF")
    private Integer leaf;

    /**
     * 级别
     */
    @TableField("GRADE")
    private Integer grade;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
