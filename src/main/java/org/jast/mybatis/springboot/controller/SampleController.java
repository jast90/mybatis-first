package org.jast.mybatis.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiwen on 15-4-19.
 */
@RestController
public class SampleController {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello")
    public Object String(){
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        return list;
    }
}
