package me.qiancheng.qianworks.meicai.controller;

import com.sun.javafx.tools.packager.Log;
import com.sun.org.apache.xpath.internal.operations.String;
import me.qiancheng.qianworks.meicai.base.Result;
import me.qiancheng.qianworks.meicai.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by iamya on 6/25/2016.
 */
@RestController
public class UserController {


    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private OauthService oauthService;

    @RequestMapping(value = "/user/auth.json")
    public Result auth(@RequestParam(value = "username", required = false) String username,
                       @RequestParam(value = "email", required = false) String email,
                       @RequestParam(value = "password") String password) {

        Log.debug("::::::::");
        return null;
    }
}
