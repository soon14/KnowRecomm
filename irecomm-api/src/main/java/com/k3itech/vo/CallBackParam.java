package com.k3itech.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:dell
 * @since: 2021-06-05
 */
@Data
@ApiModel(value = "回调参数")
public class CallBackParam {
    @ApiModelProperty(value = "知识id")
    private String md5id;
    @ApiModelProperty(value = "用户操作，0喜欢，1不喜欢，2点击",example = "1")
    private int islike;
    @ApiModelProperty(value = "用户证件号")
    private String pid;
}
