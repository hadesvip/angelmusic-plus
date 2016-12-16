package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.ActivationCodeService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
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

        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("get")) {
            error("此接口只支持post请求");
            return;
        }

        final String activationCode = getPara("activationCode");
        if (StrKit.isBlank(activationCode)) {
            error(HttpCode.ACTIVATION_CODE_EMPTY, HttpCode.ACTIVATION_CODE_EMPTY_WORD);
            return;
        }

        //激活码激活
        renderJson(ActivationCodeService.ACTIVATIONCODESERVICE.activateCode(activationCode));

        LOGGER.info("[leave activationCode method]");
    }
}
