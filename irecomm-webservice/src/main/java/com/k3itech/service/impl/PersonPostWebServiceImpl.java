package com.k3itech.service.impl;

import com.k3itech.irecomm.re.entity.PersonPost;
import com.k3itech.irecomm.re.service.IPersonPostService;
import com.k3itech.service.IPersonPostWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;

/**
 * @author:dell
 * @since: 2021-05-28
 */
@Service
@WebService(name = "IPersonPostWebService",
        targetNamespace = "http://service.k3itech.com",
        endpointInterface = "com.k3itech.service.IPersonPostWebService"
)
public class PersonPostWebServiceImpl implements IPersonPostWebService {
    @Autowired
    private IPersonPostService iuserPostService;


    @Override
    public boolean saveOrUpdatePost(String identityNo, String vdefl) {
        PersonPost userPost = new PersonPost();
        userPost.setIdentityNo(identityNo);
        userPost.setVdefl(vdefl);

        boolean flag=iuserPostService.saveOrUpdate(userPost);
       return flag;
    }

    @Override
    public boolean deletePost(String identityNo) {
       return iuserPostService.removeById(identityNo);
    }


}
