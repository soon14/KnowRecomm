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
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ADMIN_ORG")
public class AdminOrg extends Model<AdminOrg> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID---CASIC_ORG_CODE
     */
    @TableId("ID")
    private String id;

    /**
     * 组织名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 父级组织ID
     */
    @TableField("PARENT_ID")
    private String parentId;

    /**
     * 机构层级
     */
    @TableField("ORG_LEVEL")
    private Integer orgLevel;

    /**
     * 保密资格等级---CASIC_ORG_SECRET
     */
    @TableField("ORG_SECRET")
    private String orgSecret;

    /**
     * 单位别名
     */
    @TableField("EXTERNAL_NAME")
    private String externalName;

    /**
     * 本级单位排序值
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 由更新类型synctype判断得出：新增和修改为0，删除为1
     */
    @TableField("DELETED")
    private String deleted;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("CRT_TIME")
    private Date crtTime;

    @TableField("CRT_USER")
    private String crtUser;

    @TableField("CRT_NAME")
    private String crtName;

    @TableField("CRT_HOST")
    private String crtHost;

    @TableField("UPD_TIME")
    private Date updTime;

    @TableField("UPD_USER")
    private String updUser;

    @TableField("UPD_NAME")
    private String updName;

    @TableField("UPD_HOST")
    private String updHost;

    @TableField("ATTR1")
    private String attr1;

    @TableField("ATTR2")
    private String attr2;

    @TableField("ATTR3")
    private String attr3;

    @TableField("ATTR4")
    private String attr4;

    /**
     * 组织编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 组织编码层级展示
     */
    @TableField("PATH_CODE")
    private String pathCode;

    /**
     * 组织名称层级展示
     */
    @TableField("PATH_NAME")
    private String pathName;

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
        return this.id;
    }

}
