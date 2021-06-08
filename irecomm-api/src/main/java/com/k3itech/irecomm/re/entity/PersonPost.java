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
@TableName("PERSON_POST")
public class PersonPost extends Model<PersonPost> {

    private static final long serialVersionUID = 1L;

    /**
     * 身份证
     */
    @TableId("IDENTITY_NO")
    private String identityNo;

    /**
     * 岗位
     */
    @TableField("VDEFL")
    private String vdefl;

    @TableField("CRT_TIME")
    private Timestamp crtTime;


    @Override
    protected Serializable pkVal() {
        return this.identityNo;
    }

}
