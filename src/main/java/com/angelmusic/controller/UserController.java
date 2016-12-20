package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.jfinal.ext.route.ControllerBind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户
 * Created by wangyong on 16-12-19.
 */
@ControllerBind(controllerKey = "webapi/user/")
public class UserController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 上传用户信息
     */
    public void uploadUserInfo() {

    }
}
