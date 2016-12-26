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

        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("get")) {
            error("此接口只支持post请求");
            return;
        }

        String userPhone = getPara("userPhone");

        //手机号为空
        if (StrKit.isBlank(userPhone)) {
            error(HttpCode.USER_PHONE_EMPTY, HttpCode.USER_PHONE_EMPTY_WORD);
            return;
        }

        //保存用户信息
        renderJson(UserService.ME.uploadUserInfo(userPhone));

        LOGGER.info("[leave uploadUserInfo]");
    }

    /**
     * 获取用户信息
     */
    public void getUser() {
        LOGGER.info("[invoke getUser]");

        String userPhone = getPara("userPhone");

        //手机号为空
        if (StrKit.isBlank(userPhone)) {
            error(HttpCode.USER_PHONE_EMPTY, HttpCode.USER_PHONE_EMPTY_WORD);
            return;
        }
        renderJson(UserService.ME.getUser(userPhone));

        LOGGER.info("[leave getUser]");
    }
}
