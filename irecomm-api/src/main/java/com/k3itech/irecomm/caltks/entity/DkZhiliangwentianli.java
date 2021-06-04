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
 * 质量案例信息表
 * </p>
 *
 * @author jobob
 * @since 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("CALTKS_DK_ZHILIANGWENTIANLI")
public class DkZhiliangwentianli extends Model<DkZhiliangwentianli> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private BigDecimal id;

    @TableField("XINGHAOMINGCHENG")
    private String xinghaomingcheng;

    @TableField("XINGHAOLEIBIE")
    private String xinghaoleibie;

    @TableField("CHANPINDAIHAO")
    private String chanpindaihao;

    @TableField("CHANPINBIANHAO")
    private String chanpinbianhao;

    @TableField("CHANPINMINGCHENG")
    private String chanpinmingcheng;

    @TableField("SUOSHUFENXITONG")
    private String suoshufenxitong;

    @TableField("GUZHANGSHIJIAN")
    private String guzhangshijian;

    @TableField("GONGZUOJIEDUAN")
    private String gongzuojieduan;

    @TableField("GUZHANGGAISHU")
    private String guzhanggaishu;

    @TableField("YUANYINFENXI")
    private String yuanyinfenxi;

    @TableField("YUANYINFENLEI")
    private String yuanyinfenlei;

    @TableField("YUANYINFENLEI2")
    private String yuanyinfenlei2;

    @TableField("PICIXINGWENTI")
    private String picixingwenti;

    @TableField("JIUZHENGCUOSHI")
    private String jiuzhengcuoshi;

    @TableField("WAIXIEGUANLIYUANYIN")
    private String waixieguanliyuanyin;

    @TableField("JIAFANGGUANLI")
    private String jiafangguanli;

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
