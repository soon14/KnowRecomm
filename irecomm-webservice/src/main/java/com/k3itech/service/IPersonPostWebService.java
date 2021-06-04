package com.k3itech.service;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * @author:dell
 * @since: 2021-05-28
 */

@WebService(name  = "IPersonPostWebService",
        targetNamespace = "http://service.k3itech.com"
)
public interface IPersonPostWebService {

   /* @WebMethod
    boolean saveBatch(@WebParam(name = "personPostList") List<PersonPost> personPostList);*/

    /**
     *
     * @param identityNo
     * @param vdefl
     * @return
     */
    @WebMethod
    boolean saveOrUpdatePost(@WebParam(name="identityNo") String identityNo,@WebParam(name="vdefl") String vdefl);

    /**
     *
     * @param identityNo
     * @return
     */
    @WebMethod
    boolean deletePost(@WebParam(name="identityNo") String identityNo);
}
