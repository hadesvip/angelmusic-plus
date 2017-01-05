package com.angelmusic.service;

import com.angelmusic.dao.model.ActivationCode;
import com.angelmusic.dao.model.OrderRecord;
import com.angelmusic.dao.model.Topic;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.UUID;

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
    public Ret activateCode(String code, String account) {

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

        //更新激活码状态并生成订单
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        saveActivationCode(orderId, code, account);

        //更新订单的权益开始和结束时间
        OrderService.ME.calcOrderStartEndTime(account, Constant.ORDER_TYPE_ACTIVATECODE, code, orderId, Constant.PAY_SUCESS);

        //算出解锁主题
        Topic topic = OrderService.calcUserTopicNum(account);
        return Ret.create("code", HttpCode.SUCCESS).put("detail", topic);
    }

    @Before(Tx.class)
    private void saveActivationCode(String orderId, String code, String account) {
        ActivationCode.ME.updateActivationCodeStatus(code);
        OrderRecord.ME.saveOrderRecord(orderId, account, "0", code, Constant.ORDER_TYPE_ACTIVATECODE, Constant.PAY_SUCESS);
    }
}
