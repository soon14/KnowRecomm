package com.k3itech.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.irecomm.re.entity.IreRecommLog;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.entity.IreUserRecommresult;
import com.k3itech.irecomm.re.service.IIreRecommLogService;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.irecomm.re.service.IIreUserRecommresultService;
import com.k3itech.service.PostService;
import com.k3itech.service.RedisService;
import com.k3itech.utils.CommonConstants;
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
import java.util.*;

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
    private RedisService redisService;

    @Autowired
    private IIreUserRecommresultService iIreUserRecommresultService;

    @Autowired
    private IIreRecommLogService iIreRecommLogService;

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
        ireUserRecommresult.setKnowledge(callBackParam.getMd5id());
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
            orgqueryWrapper.apply("ORG_CODE"+" like {0}",org).or(true);
        }
        List<String> userids= new ArrayList<>();
        List<IreUserFollow> ireUserFollows = iIreUserFollowService.list(orgqueryWrapper);
        for (IreUserFollow ireUserFollow:ireUserFollows){
           boolean postresult = postService.postKnowledge(ireUserFollow);
            if (!postresult){
                userids.add(ireUserFollow.getIdNum());
            }
        }
        redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.POST_OVER_FLG);
        if (ObjectUtils.isNotEmpty(userids)){
            return R.failed(userids,"以下用户云雀消息发送失败");
        }

        return R.ok("发送成功");


    }

    @GetMapping("/postfailmessage")
    @ApiOperation(value = "失败补发")
    @ResponseBody
    public Object postFailMessage(){

        log.info("start post failmessage");
        QueryWrapper<IreRecommLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS",CommonConstants.STATUS_FAIL)
                .notInSql("(ID_NUM,KNOWLEDGE)","SELECT ID_NUM,KNOWLEDGE  FROM IRE_RECOMM_LOG WHERE STATUS='0'");
        List<String> success= new ArrayList<>();
        List<String> fail= new ArrayList<>();
        List<IreRecommLog> recommLogs=iIreRecommLogService.list(queryWrapper);
        for (IreRecommLog recommLog:recommLogs) {
            IreUserFollow ireUserFollow=iIreUserFollowService.getById(recommLog.getIdNum());
           boolean flag= postService.postKnowledge(ireUserFollow);
           if (flag){
               success.add(ireUserFollow.getIdNum());
           }else {
               fail.add(ireUserFollow.getIdNum());
           }
        }
        Map<String,List<String>> result= new HashMap<>();
        result.put("补发成功",success);
        result.put("补发失败",fail);

        redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.POST_OVER_FLG);

        return R.ok(result);


    }
}
