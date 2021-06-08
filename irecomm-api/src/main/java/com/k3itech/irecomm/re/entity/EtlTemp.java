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
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ETL_TEMP")
public class EtlTemp extends Model<EtlTemp> {

    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    @TableId("ID")
    private String id;

    /**
     * 抽取时间
     */
    @TableField("EXTRACT_TIME")
    private Timestamp extractTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
