package com.k3itech.irecomm.caltks.entity;

import java.math.BigDecimal;
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
@TableName("CALTKS_TREE_NODE")
public class TreeNode extends Model<TreeNode> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private BigDecimal id;

    @TableField("TREE_NODE_TYPE")
    private String treeNodeType;

    @TableField("NODE_DESCRIPTION")
    private String nodeDescription;

    @TableField("CODE")
    private String code;

    @TableField("NODE_NAME")
    private String nodeName;

    @TableField("PARENT_ID")
    private BigDecimal parentId;

    @TableField("ORDER_ID")
    private BigDecimal orderId;

    @TableField("ORG_CODE")
    private String orgCode;

    @TableField("PATH_CODE")
    private String pathCode;

    @TableField("PATH_NAME")
    private String pathName;

    @TableField("ORG_SECRET")
    private String orgSecret;

    @TableField("EXTERNAL_NAME")
    private String externalName;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("PARENT_CODE")
    private String parentCode;

    @TableField("ORG_LEVEL")
    private BigDecimal orgLevel;

    @TableField("LAST_TIME")
    private Timestamp lastTime;

    /**
     * 删除标志
     */
    @TableField("ISDEL")
    private BigDecimal isdel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
