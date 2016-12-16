package com.angelmusic.service;

import com.angelmusic.dao.model.ActivationCode;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 激活码服务层
 * Created by wangyong on 16-12-16.
 */
public class ActivationCodeService {

    public static final ActivationCodeService ACTIVATIONCODESERVICE = new ActivationCodeService();

    /**
     * 激活码激活
     *
     * @param code 激活码
     * @return
     */
    @Before(Tx.class)
    public HttpResult activateCode(String code) {

        //获取激活码信息
        final ActivationCode activationCodeInfo = ActivationCode.ME.getActivationCodeByCode(code);

        //激活码不存在
        if (activationCodeInfo == null) {
            return new HttpResult(HttpCode.ACTIVATION_CODE_NOT_EXISTS, HttpCode.ACTIVATION_CODE_NOT_EXISTS_WORD);
        }
        //激活码已经激活过
        if (activationCodeInfo.getInt("status") == Constant.ACTIVATION_CODE_ACTIVATED) {
            return new HttpResult(HttpCode.ACTIVATION_CODE_ACTIVATED, HttpCode.ACTIVATION_CODE_ACTIVATED_WORD);
        }

        //更改激活码状态为已激活
        boolean activateResult = ActivationCode.ME.updateActivationCodeStatus(code);

        //激活成功
        if (activateResult) {
            return new HttpResult(HttpCode.SUCCESS, HttpCode.ACTIVATION_CODE_ACTIVATED_OK);
        }
        return new HttpResult(HttpCode.FAIL, HttpCode.FAIL_WORD);
    }
}
