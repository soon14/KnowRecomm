package com.k3itech.irecomm.re.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_PERSON_JOB")
public class IrePersonJob extends Model<IrePersonJob> {

    private static final long serialVersionUID = 1L;

    /**
     * 身份证
     */
    @TableId("ID_NUM")
    private String idNum;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 组织
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 类别
     */
    @TableField("TYPE")
    private String type;

    /**
     * 专业
     */
    @TableField("PROFFESSIONAL")
    private String proffessional;

    /**
     * 方向
     */
    @TableField("DIRECTION")
    private String direction;


    @Override
    protected Serializable pkVal() {
        return this.idNum;
    }

}
