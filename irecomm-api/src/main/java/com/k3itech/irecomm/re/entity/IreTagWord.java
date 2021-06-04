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
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IRE_TAG_WORD")
public class IreTagWord extends Model<IreTagWord> {

    private static final long serialVersionUID = 1L;

    /**
     * 词语
     */
    @TableId("WORD")
    private String word;

    /**
     * 词语类型（型号、设备分系统、阶段、领域、军兵种、指标、专业方向等）
     */
    @TableField("TAG_TYPE")
    private String tagType;


    @Override
    protected Serializable pkVal() {
        return this.word;
    }

}
