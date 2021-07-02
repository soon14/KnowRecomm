package com.k3itech.irecomm.re.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_PERSON_JOB")
public class IrePersonJob extends Model<IrePersonJob> {

    private static final long serialVersionUID = 1L;

    /**
     * 身份证
     */
    private String idNum;

    /**
     * 姓名
     */
    private String name;

    /**
     * 组织
     */
    private String orgCode;

    /**
     * 类别
     */
    private String type;

    /**
     * 专业
     */
    private String professional;

    /**
     * 方向
     */
    private String direction;


    @Override
    protected Serializable pkVal() {
        return this.idNum;
    }

}
