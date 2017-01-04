package com.angelmusic.service;

import com.angelmusic.dao.model.ActivationCode;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 激活码服务层
 * Created by wangyong on 16-12-16.
 */
public class ActivationCodeService {

    public static final ActivationCodeService ME = new ActivationCodeService();

    /**
     * 激活码激活
     *
     * @param code 激活码
     * @return
     */
    @Before(Tx.class)
    public Ret activateCode(String code) {

        //获取激活码信息
        final ActivationCode activationCodeInfo = ActivationCode.ME.getActivationCodeByCode(code);

        //激活码不存在
        if (activationCodeInfo == null) {
            return Ret.create("code", HttpCode.ACTIVATION_CODE_NOT_EXISTS);
        }
        //激活码已经激活过
        if (activationCodeInfo.getInt("status") == Constant.ACTIVATION_CODE_ACTIVATED) {
            return Ret.create("code", HttpCode.ACTIVATION_CODE_ACTIVATED);
        }

        //激活
        ActivationCode.ME.updateActivationCodeStatus(code);
        
        return null;
    }
}
