package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.UserService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户
 * Created by wangyong on 16-12-19.
 */
@ControllerBind(controllerKey = "webapi/user")
public class UserController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 上传用户信息
     */
    public void uploadUserInfo() {
        LOGGER.info("[invoke uploadUserInfo]");
        String userPhone = getPara("userPhone");

        //手机号为空
        if (StrKit.isBlank(userPhone)) {
            error(HttpCode.USER_PHONE_EMPTY, HttpCode.USER_PHONE_EMPTY_WORD);
            return;
        }

        //保存用户信息
        renderJson(UserService.USERSERVICE.uploadUserInfo(userPhone));

        LOGGER.info("[leave uploadUserInfo]");
    }
}
