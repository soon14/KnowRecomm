package com.k3itech.irecomm.caltks.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 知识的基础信息
 * </p>
 *
 * @author jobob
 * @since 2021-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("CALTKS_META_KNOWLEDGE")
public class MetaKnowledge extends Model<MetaKnowledge> {

    private static final long serialVersionUID = 1L;

    @TableField("ID")
    private BigDecimal id;

    @TableField("ABSTRACT_TEXT")
    private String abstractText;

    @TableField("SECURITY_LEVEL")
    private String securityLevel;

    @TableField("KNOWLEDGE_SOURCE_FILE_PATH")
    private String knowledgeSourceFilePath;

    @TableField("KTYPEID")
    private BigDecimal ktypeid;

    @TableField("DOMAIN_NODEID")
    private BigDecimal domainNodeid;

    @TableField("UPLOAD_TIME")
    private Timestamp uploadTime;

    @TableField("UPLOADERID")
    private BigDecimal uploaderid;

    @TableField("IS_VISIBLE")
    private Integer isVisible;

    @TableField("STATUS")
    private String status;

    @TableField("IDENTIFIER")
    private String identifier;

    @TableField("FLASH_FILE_PATH")
    private String flashFilePath;

    @TableField("TITLE_NAME")
    private String titleName;

    @TableField("VERID")
    private BigDecimal verid;

    @TableField("COMMENTRECORDID")
    private BigDecimal commentrecordid;

    @TableField("KNOWLEDGETYPE_ID")
    private BigDecimal knowledgetypeId;

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
        return null;
    }

}
