package com.k3itech;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreKnowledgeInfoService;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.service.CalculateService;
import com.k3itech.utils.R;
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
    public Object postMessage(){

        log.info("start calc by manual trigger");
        List<IreUserFollow> ireUserFollowList = iIreUserFollowService.list();
        List<IreKnowledgeInfo> ireKnowledgeInfos = iIreKnowledgeInfoService.list();
        for (IreUserFollow ireUserFollow : ireUserFollowList) {
            calculateService.compareU2K(ireUserFollow, ireKnowledgeInfos);
        }log.info("calc end by manual trigger");

        return R.ok();


    }
}
