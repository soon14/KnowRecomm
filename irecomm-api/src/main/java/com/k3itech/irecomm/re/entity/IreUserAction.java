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
 * 用户行为表
 * </p>
 *
 * @author jobob
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_USER_ACTION")
public class IreUserAction extends Model<IreUserAction> {

    private static final long serialVersionUID = 1L;

    @TableId("ID_NUM")
    private String idNum;

    /**
     * 操作类型
     */
    @TableField("OPERATE_TYPE")
    private String operateType;

    /**
     * 操作对象
     */
    @TableField("OPERATE_OBJECT")
    private String operateObject;

    /**
     * 操作时间
     */
    @TableField("OPERATE_TIME")
    private Timestamp operateTime;


    @Override
    protected Serializable pkVal() {
        return this.idNum;
    }

}
