package org.jast.mybatis.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhiwen on 15-7-19.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "login")
    public String login(){
        return "login";
    }

}
