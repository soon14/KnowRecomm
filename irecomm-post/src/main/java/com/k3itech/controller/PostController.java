package com.k3itech.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.entity.IreUserRecommresult;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.irecomm.re.service.IIreUserRecommresultService;
import com.k3itech.service.PostService;
import com.k3itech.utils.ObjectUtils;
import com.k3itech.utils.R;
import com.k3itech.vo.CallBackParam;
import com.k3itech.vo.Page;
import com.k3itech.vo.RecommContent;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:dell
 * @since: 2021-06-04
 */
@RestController
@RequestMapping("/irecommpost")
@Slf4j
@EnableSwagger2
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private IIreUserFollowService iIreUserFollowService;

    @Autowired
    private IIreUserRecommresultService iIreUserRecommresultService;

    @Value("${calc.orgcode}")
    private String orgcode;

    static final String SPLITCHR= ",";


    @GetMapping("/getresults")
    @ApiOperation(value = "获取匹配结果信息")
    @ResponseBody
    public Object getPostResults(Page page){
        QueryWrapper<IreUserFollow> ireUserFollowQueryWrapper= new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(page.getId_num())) {
            ireUserFollowQueryWrapper.like("ID_NUM",page.getId_num());
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page page1= new com.baomidou.mybatisplus.extension.plugins.pagination.Page();
        page1.setCurrent(page.getCurrent());
        page1.setSize(page.getSize());
        com.baomidou.mybatisplus.extension.plugins.pagination.Page ireUserFollowpage = iIreUserFollowService.page(page1,ireUserFollowQueryWrapper);
        List<IreUserFollow> ireUserFollows=ireUserFollowpage.getRecords();
        Map<String,List<RecommContent>> records=new HashMap<>();
        for (IreUserFollow ireUserFollow:ireUserFollows){
            List<RecommContent> recommContents = postService.getKnowledge(ireUserFollow);
            records.put(ireUserFollow.getIdNum(),recommContents);

        }

        return R.ok(records);


    }

    @GetMapping("/getCallback")
    @ApiOperation(value = "获取用户喜好返回信息")
    @ResponseBody
    public Object getCallback(CallBackParam callBackParam){

        IreUserRecommresult ireUserRecommresult= new IreUserRecommresult();
        ireUserRecommresult.setIdNum(callBackParam.getPid());
        ireUserRecommresult.setIslike(callBackParam.getIslike()+"");
        Date day = new Date();
        Timestamp localDateTime = new Timestamp(day.getTime());
        ireUserRecommresult.setUpdateTime(localDateTime);
        boolean flag=iIreUserRecommresultService.save(ireUserRecommresult);
        if (!flag){
            return R.failed();
        }
        return R.ok();


    }


    @GetMapping("/postmessage")
    @ApiOperation(value = "手动触发post")
    @ResponseBody
    public Object postMessage(){

        log.info("start post ");
        QueryWrapper<IreUserFollow> orgqueryWrapper= new QueryWrapper();
        for (String org:orgcode.split(SPLITCHR)) {
            orgqueryWrapper.like("ORG_CODE",org).or(true);
        }
        List<IreUserFollow> ireUserFollows = iIreUserFollowService.list(orgqueryWrapper);
        for (IreUserFollow ireUserFollow:ireUserFollows){
           boolean postresult = postService.postKnowledge(ireUserFollow);
            if (!postresult){

            }
        }
        return R.ok();


    }
}
