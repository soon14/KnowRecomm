package com.k3itech.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreKnowledgeInfoService;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.irecomm.yunque.entity.ZzMessageInfo;
import com.k3itech.service.CalculateService;
import com.k3itech.utils.R;
import com.k3itech.utils.SecretLevel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author:dell
 * @since: 2021-06-05
 */

@RestController
@RequestMapping("/irecommcalc")
@Slf4j
@EnableSwagger2
public class CalcController {

    @Autowired
    CalculateService calculateService;
    @Autowired
    IIreUserFollowService iIreUserFollowService;
    @Autowired
    IIreKnowledgeInfoService iIreKnowledgeInfoService;

    @GetMapping("/calc")
    @ApiOperation(value = "手动触发calc")
    @ResponseBody
    public Object calcManual(){

        log.info("start calc by manual trigger");
        List<IreUserFollow> ireUserFollowList = iIreUserFollowService.list();

        for (IreUserFollow ireUserFollow : ireUserFollowList) {
            String secretlevel=ireUserFollow.getSecretLevel();
            QueryWrapper<IreKnowledgeInfo> queryWrapper= new QueryWrapper();
            queryWrapper.in("SECURITY_LEVEL", SecretLevel.getKnowledgeSecretLevelsByUserLevel(secretlevel));
            List<IreKnowledgeInfo> ireKnowledgeInfos = iIreKnowledgeInfoService.list(queryWrapper);
            calculateService.compareU2K(ireUserFollow, ireKnowledgeInfos);
        }log.info("calc end by manual trigger");

        return R.ok();


    }
}
