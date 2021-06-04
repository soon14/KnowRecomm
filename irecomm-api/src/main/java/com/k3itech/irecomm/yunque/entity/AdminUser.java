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
 * @since 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ADMIN_USER")
public class AdminUser extends Model<AdminUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private String id;

    /**
     * 姓名---OPERATOR_NAME
     */
    @TableField("NAME")
    private String name;

    /**
     * 身份证号
     */
    @TableField("P_ID")
    private String pId;

    /**
     * 组织ID---CASIC_ORG_CODE
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 所在机构名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 密级-30：非密；40：一般一类；50：一般二类；60：重要一类；70：重要二类；80：核心一类；90：核心二类
     */
    @TableField("SECRET_LEVEL")
    private String secretLevel;

    /**
     * 性别---1：男；2：女；3：未知
     */
    @TableField("GENDER")
    private String gender;

    /**
     * 排序号
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 出入证号
     */
    @TableField("EMP_CODE")
    private String empCode;

    /**
     * 生日
     */
    @TableField("BIRTH_DATE")
    private Date birthDate;

    /**
     * 办公电话
     */
    @TableField("O_TEL")
    private String oTel;

    /**
     * 办公邮件
     */
    @TableField("O_EMAIL")
    private String oEmail;

    /**
     * 行政岗位
     */
    @TableField("WORK_POST")
    private String workPost;

    /**
     * 技术岗位
     */
    @TableField("TEC_POST")
    private String tecPost;

    /**
     * 由更新类型synctype判断得出：新增和修改为0，删除为1
     */
    @TableField("DELETED")
    private String deleted;

    /**
     * 姓
     */
    @TableField("REFA")
    private String refa;

    /**
     * 名
     */
    @TableField("REFB")
    private String refb;

    /**
     * 头像
     */
    @TableField("AVATAR")
    private String avatar;

    /**
     * 描述
     */
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
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    @TableField("ATTR5")
    private String attr5;

    @TableField("ATTR6")
    private String attr6;

    @TableField("ATTR7")
    private String attr7;

    @TableField("ATTR8")
    private String attr8;

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
