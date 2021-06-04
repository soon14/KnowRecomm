package com.k3itech.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:dell
 * @since: 2021-06-04
 */
@Data
@ApiModel(value = "页码信息")
public class Page {
    @ApiModelProperty(value = "每页记录数")
    private int size;
    @ApiModelProperty(value = "当前页")
    private int current;
    @ApiModelProperty(value = "身份证号")
    private String id_num;

    public Page(){
        this.size=10;
        this.current=1;
    }
}

