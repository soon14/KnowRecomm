package com.k3itech.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "知识结果")
public class KnowledgeResult {
    @ApiModelProperty(value = "文件名")
    public String fileName;
    @ApiModelProperty(value = "文件类型 .doc")
    public String fileType;
    @ApiModelProperty(value = "作者")
    public String author="云雀";
    @ApiModelProperty(value = "来源，PDM/知识管理")
    public String source="知识";
    @ApiModelProperty(value = "下载地址")
    public String path;
    @ApiModelProperty(value = "回调地址（提交点击结果）")
    public String callback;


}
