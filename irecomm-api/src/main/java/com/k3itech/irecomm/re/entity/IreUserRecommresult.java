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
 * 推荐结果用户喜好度
 * </p>
 *
 * @author jobob
 * @since 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_USER_RECOMMRESULT")
public class IreUserRecommresult extends Model<IreUserRecommresult> {

    private static final long serialVersionUID = 1L;

    @TableId("ID_NUM")
    private String idNum;

    /**
     * 知识
     */
    @TableField("KNOWLEDGE")
    private String knowledge;

    /**
     * 0喜欢,-1不喜欢
     */
    @TableField("ISLIKE")
    private Integer islike;


    @Override
    protected Serializable pkVal() {
        return this.idNum;
    }

}
