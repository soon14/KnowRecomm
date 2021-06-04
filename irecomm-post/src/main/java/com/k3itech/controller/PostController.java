package com.k3itech.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.service.PostService;
import com.k3itech.utils.ObjectUtils;
import com.k3itech.utils.R;
import com.k3itech.vo.Page;
import com.k3itech.vo.RecommContent;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
}
