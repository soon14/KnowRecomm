package com.k3itech.utils;

/**
 * @author:dell
 * @since: 2021-07-06
 */
public class PostUtils {
    public static String getKnowledgeURL(String knowledgeip,String knowledgeid,String userid){
        String url = knowledgeip + "/giksp/ui!clientsearch.action?kid=" + knowledgeid + "&kname=&j_username=" + userid + "&flag=client ";

        return url;
    }

    public static String getCallBackURL(String urlinterface,String ipport,String knowledgeid,String userid,String taskname){
        String url = "/"+urlinterface+"/getCallback?md5id="+knowledgeid+"&pid="+userid+"&islike=";
        if (ObjectUtils.isNotEmpty(taskname)) {
            url = "/" + urlinterface + "/getCallback?md5id=" + knowledgeid + "&pid=" + taskname + "-" + userid + "&islike=";
        }

        return url;
    }
}
