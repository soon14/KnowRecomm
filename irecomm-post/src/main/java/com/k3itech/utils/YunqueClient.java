package com.k3itech.utils;

import com.k3itech.vo.YunqueContent;
import lombok.extern.slf4j.Slf4j;
import org.openapisdk.entity.OpenApiRequestParamVo;
import org.openapisdk.request.NoticeRequest;
import org.openapisdk.response.ApiResponse;
import org.openapisdk.service.OpenApiService;

import java.util.Map;

/**
 * @author:dell
 * @since: 2021-07-06
 */
@Slf4j
public class YunqueClient {

    public boolean sendMessage(YunqueContent yunqueContent){
        Object object = null;
        Object message = null;
        Object status = null;

        OpenApiService service = new OpenApiService();
        NoticeRequest request = new NoticeRequest();
        request.setMsgContent(yunqueContent.getMsgContent());
        request.setReceiverId(yunqueContent.getReceiverId());
        request.setReceiverName(yunqueContent.getReceiverName());
        request.setNoticeLevel(yunqueContent.getLevel());
        request.setSenderOrgName(yunqueContent.getSenderOrgName());
        request.setBz(yunqueContent.getBz());
        request.setSenderType(yunqueContent.getSenderType());
        OpenApiRequestParamVo paramVo = new OpenApiRequestParamVo();
        paramVo.setApiUrl("/openApi/service");
        paramVo.setServiceId("AvRqvvAY");


        paramVo.setRequestBody(request);
        paramVo.setToken("oeadmOczKMmUw2jnDoimdSZEWIAHqTxDwDkYiBy9uPscrLHx");
        try {
            ApiResponse<Map<String, Object>> map = service.doPostMethod(paramVo);
            System.out.println(map);
            object = map.getStatus();
            if (!object.equals(200)) {
                return false;
            }
            Map<String, Object> res = (Map<String, Object>) map.getResult().get("res");
            System.out.println("res " + res);
            if (ObjectUtils.isNotEmpty(res)) {
                message = res.get("message");
                System.out.println(message);
                status = res.get("status");
                System.out.println(status);
            }
        }catch (Exception e){
            log.error("云雀接口调用错误："+e.getMessage());
            e.printStackTrace();

            return false;
        }

      if ( ObjectUtils.isNotEmpty(object) && ObjectUtils.isNotEmpty(status) && ObjectUtils.isNotEmpty(message) && status.equals(200) && message.equals("操作成功")){
          return true;
      }

        return false;

    }
}
