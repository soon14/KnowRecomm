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
 * 知识信息
 * </p>
 *
 * @author jobob
 * @since 2021-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_KNOWLEDGE_INFO")
public class IreKnowledgeInfo extends Model<IreKnowledgeInfo> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 时间
     */
    @TableField("CREATE_TIME")
    private Timestamp createTime;

    /**
     * 作者
     */
    @TableField("AUTHOR")
    private String author;

    /**
     * 密级
     */
    @TableField("SECURITY_LEVEL")
    private String securityLevel;

    /**
     * 摘要
     */
    @TableField("ABSTRACT_TEXT")
    private String abstractText;

    /**
     * 组织
     */
    @TableField("ORG")
    private String org;

    /**
     * 知识类型
     */
    @TableField("KTYPE")
    private String ktype;

    /**
     * 相关型号
     */
    @TableField("TAG_MODEL")
    private String tagModel;

    /**
     * 来源
     */
    @TableField("KNOWLEDGE_SOURCE")
    private String knowledgeSource;

    /**
     * 关键词标签
     */
    @TableField("TAG_KEYWORDS")
    private String tagKeywords;

    /**
     * 相关设备
     */
    @TableField("TAG_DEVICE")
    private String tagDevice;

    /**
     * 源表id
     */
    @TableField("SOURCE_ID")
    private String sourceId;

    /**
     * url地址
     */
    @TableField("URL")
    private String url;

    /**
     * 领域
     */
    @TableField("DOMAIN")
    private String domain;

    /**
     * 更新时间
     */
    @TableField("LAST_TIME")
    private Timestamp lastTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
