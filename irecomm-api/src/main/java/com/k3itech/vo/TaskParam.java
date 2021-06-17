package com.k3itech.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "任务匹配知识输入参数")
public class TaskParam {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "身份证号（唯一）",required = true)
    private String userPId;
    @ApiModelProperty(value = "任务名称",required = true)
    private String taskName	;
    @ApiModelProperty(value = "任务所在项目名称",required = true)
    private String projectName	;
    @ApiModelProperty(value = "密级 30:非密 ,40:mm , 60:jm",required = true)
    private String secretLevel;
    @ApiModelProperty(value = "任务输入数据项名称集合 如：”输入项一，输入项二”")
    private String inputData;
    @ApiModelProperty(value = "任务输出数据项名称集合 如：”输出项一，输出项二，输出项三”")
    private String outputData;
    @ApiModelProperty(value = "任务关联工具名称集合 如：”工具一，工具二”")

    private String tools;
}
