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
 * 使用BLOB存储的知识源文件信息
 * </p>
 *
 * @author jobob
 * @since 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("CALTKS_SYSTEM_FILE")
public class SystemFile extends Model<SystemFile> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private BigDecimal id;

    @TableField("FILE_NAME")
    private String fileName;

    @TableField("FILE_TYPE")
    private String fileType;

    @TableField("FILE_BINARY")
    private byte[] fileBinary;

    @TableField("SAVE_DATE")
    private Timestamp saveDate;

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
