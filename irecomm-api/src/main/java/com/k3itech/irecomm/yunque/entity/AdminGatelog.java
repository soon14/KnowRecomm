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
 * @since 2021-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ADMIN_GATELOG")
public class AdminGatelog extends Model<AdminGatelog> {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId("ID")
    private String id;

    /**
     * 菜单
     */
    @TableField("MENU")
    private String menu;

    /**
     * 操作
     */
    @TableField("OPT")
    private String opt;

    /**
     * 资源路径
     */
    @TableField("URI")
    private String uri;

    /**
     * 操作时间
     */
    @TableField("CRT_TIME")
    private Date crtTime;

    /**
     * 操作人ID
     */
    @TableField("CRT_USER")
    private String crtUser;

    /**
     * 操作人
     */
    @TableField("CRT_NAME")
    private String crtName;

    /**
     * 操作主机
     */
    @TableField("CRT_HOST")
    private String crtHost;

    @TableField("IS_SUCCESS")
    private String isSuccess;

    @TableField("P_ID")
    private String pId;

    /**
     * 详细操作内容
     */
    @TableField("LOG_DETAIL")
    private String logDetail;

    /**
     * 操作内容
     */
    @TableField("OPT_INFO")
    private String optInfo;

    @TableField("ORG_NAME")
    private String orgName;

    @TableField("ORG_CODE")
    private String orgCode;

    @TableField("PATH_CODE")
    private String pathCode;

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
