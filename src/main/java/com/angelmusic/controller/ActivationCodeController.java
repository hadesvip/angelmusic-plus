package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.ActivationCodeService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 激活码
 * Created by wangyong on 16-12-15.
 */
@ControllerBind(controllerKey = "/webapi/activationCode")
public class ActivationCodeController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    /**
     * 激活码激活
     */
    public void activate() {
        LOGGER.info("[invoke activationCode method ]");

     /*   String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("get")) {
            renderJson(Ret.create("code", HttpCode.HTTP_ONLY_SUPPORT_POST).getData());
            return;
        }*/

        final String activationCode = getPara("activationCode");
        final String account = getPara("account");

        //参数校验
        if (StrKit.isBlank(activationCode)) {
            renderJson(Ret.create("code", HttpCode.ACTIVATION_CODE_EMPTY).getData());
            return;
        }

        if (StrKit.isBlank(account)) {
            renderJson(Ret.create("code", HttpCode.USER_ACCOUNT_EMPTY).getData());
            return;
        }

        //激活码激活
        renderJson(ActivationCodeService.ME.activateCode(activationCode, account).getData());

        LOGGER.info("[leave activationCode method]");
    }
}
