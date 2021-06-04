package com.k3itech.controller;

import com.k3itech.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:dell
 * @since: 2021-05-28
 */
@RestController
@RequestMapping("/Hello")
public class TestController {
    @RequestMapping("/World")
    public Object heloWorld(@RequestParam(value = "name") String name){

        return R.ok(name);
    }
}
