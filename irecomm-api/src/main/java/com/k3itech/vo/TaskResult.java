package com.k3itech.vo;

import com.k3itech.utils.CommonConstants;
import com.k3itech.utils.R;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

/**
 * 响应信息主体
 * @author dell
 * @since 2021-05-16
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "响应信息主体")
public class TaskResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @ApiModelProperty(value = "返回标记：成功标记=200，失败标记=1")
    private int status;

    @Getter
    @Setter
    @ApiModelProperty(value = "返回信息")
    private String message;

    @Getter
    @Setter
    @ApiModelProperty(value = "数据")
    private T result;

    public static <T> TaskResult<T> ok() {
        return restResult(null, 200, null);
    }

    public static <T> TaskResult<T> ok(T result) {
        return restResult(result, 200, "查询成功");
    }

    public static <T> TaskResult<T> ok(T data, String msg) {
        return restResult(data, 200, msg);
    }

    public static <T> TaskResult<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> TaskResult<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> TaskResult<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> TaskResult<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    private static <T> TaskResult<T> restResult(T result, int code, String msg) {
        TaskResult<T> apiResult = new TaskResult<>();
        apiResult.setStatus(code);
        apiResult.setResult(result);
        apiResult.setMessage(msg);
        return apiResult;
    }
}


