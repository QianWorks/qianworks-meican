package me.qiancheng.qianworks.meicai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by iamya on 6/25/2016.
 */
@RestController
public class UserController {

    @RequestMapping("/")
    public void hello (){
        System.out.println((1/0));
    }
}
