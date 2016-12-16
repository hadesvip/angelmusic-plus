package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.dao.model.ActivationCode;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 激活码
 * Created by wangyong on 16-12-15.
 */
public class ActivationCodeController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    /**
     * 激活码激活
     */
    public void activationCode() {
        LOGGER.info("[invoke activationCode method ]");

        final String activationCode = getPara("activationCode");
        if (StrKit.isBlank(activationCode)) {
            error(HttpCode.ACTIVATION_CODE_EMPTY, HttpCode.ACTIVATION_CODE_EMPTY_WORD);
            return;
        }
        //获取激活码信息
        final ActivationCode activationCodeInfo = ActivationCode.ME.getActivationCodeByCode(activationCode);

        //激活码不存在
        if (activationCodeInfo == null) {
            error(HttpCode.ACTIVATION_CODE_NOT_EXISTS, HttpCode.ACTIVATION_CODE_NOT_EXISTS_WORD);
            return;
        }
        //激活码已经激活过
        if (activationCodeInfo.getInt("status") == Constant.ACTIVATION_CODE_ACTIVATED) {
            error(HttpCode.ACTIVATION_CODE_ACTIVATED, HttpCode.ACTIVATION_CODE_ACTIVATED_WORD);
            return;
        }

        //更改激活码状态为已激活
        boolean activateResult = ActivationCode.ME.updateActivationCodeStatus(activationCode);

        //激活成功
        if (activateResult) {
            success(HttpCode.SUCCESS, HttpCode.ACTIVATION_CODE_ACTIVATED_OK);
            return;
        }
        error(HttpCode.FAIL, HttpCode.FAIL_WORD);

        LOGGER.info("[leave activationCode method]");
    }
}
