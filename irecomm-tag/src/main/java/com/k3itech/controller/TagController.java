package com.k3itech.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.k3itech.irecomm.re.entity.IreTagWord;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreTagWordService;
import com.k3itech.service.TagService;
import com.k3itech.utils.Dicts;
import com.k3itech.utils.ObjectUtils;
import com.k3itech.utils.R;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author:dell
 * @since: 2021-06-05
 */

@RestController
@RequestMapping("/irecommtag")
@Slf4j
@EnableSwagger2
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private IIreTagWordService ireTagWordService;

    @Value("${userdict.path:}")
    private String userDict;



    @GetMapping("/tag")
    @ApiOperation(value = "手动触发tag")
    @ResponseBody
    public Object tag(){

        if (ObjectUtils.isNotEmpty(userDict)) {
            Dicts.init(userDict, StandardCharsets.UTF_8);
        }
        List<IreTagWord> ireTagWords = ireTagWordService.list();
        for (IreTagWord ireTagWord : ireTagWords) {
            CustomDictionary.add(ireTagWord.getWord());
        }
        log.info("start tag   knowledge by manual trigger");


        tagService.tagKnowledge();
        log.info("start tag   users manual trigger");
        tagService.tagUsers();
        log.info("tag end by manual trigger");
        return R.ok();


    }
}
