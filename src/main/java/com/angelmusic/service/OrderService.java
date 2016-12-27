package com.angelmusic.service;

import com.angelmusic.dao.model.ActivationCode;
import com.angelmusic.dao.model.GiftPack;
import com.angelmusic.dao.model.OrderRecord;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.UUID;

/**
 * 订单服务
 * Created by wangyong on 16-12-21.
 */
public class OrderService {

    public static final OrderService ORDERSERVICE = new OrderService();

    /**
     * 创建订单记录
     *
     * @param userPhone 用户手机号
     * @param money     消费金额
     * @param product   产品
     * @param type      支付类型
     * @return
     */
    @Before(Tx.class)
    public HttpResult createOrderRecord(String userPhone, String money, String product, int type) {

        //订单号
        String orderId = UUID.randomUUID().toString();

        //成功订单成功
        if (OrderRecord.ME.saveOrderRecord(orderId, userPhone, money, product, type)) {
            return new HttpResult(HttpCode.SUCCESS, HttpCode.ORDER_RECORD_SAVE_SUCESS_WORD, OrderRecord.ME);
        }
        return new HttpResult(HttpCode.ORDER_RECORD_CREATE_FAIL, HttpCode.ORDER_RECORD_CREATE_FAIL_WORD);
    }

    /**
     * 更新订单支付结果
     *
     * @param orderId   订单编号
     * @param payResult 支付结果
     * @return
     */
    @Before(Tx.class)
    public HttpResult updateOrderRecord(String orderId, String payResult) {

        //更新成功
        if (OrderRecord.ME.updatePayResult(orderId, payResult)) {
            return new HttpResult(HttpCode.SUCCESS, HttpCode.ORDER_UPDATE_SCUCESS_WORD);
        }

        return new HttpResult(HttpCode.ORDER_UPDATE_FAIL, HttpCode.ORDER_UPDATE_FAIL_WORD);
    }

    /**
     * 计算出用户总共几个月
     *
     * @param userPhone 用户手机号
     * @return
     */
    public int userMonths(String userPhone) {

        //计算出用户总共买了几个月
        final int[] buyMonths = {0};
        OrderRecord.ME.getUserOrderList(userPhone).forEach(orderRecord -> {
            int orderType = orderRecord.getInt("type");
            String product = orderRecord.getStr("product");

            //激活码
            if (orderType == Constant.ORDER_TYPE_ACTIVATECODE) {
                final ActivationCode activationCode = ActivationCode.ME.getActivationCodeByCode(product);
                if (activationCode != null) {
                    int effectiveTime = activationCode.getInt("effective_time");
                    buyMonths[0] += effectiveTime;
                }
            }
            //大礼包
            else {
                GiftPack giftPack = GiftPack.ME.getGiftPackByName(product);
                if (giftPack != null) {
                    int effectiveTime = giftPack.getInt("effective_time");
                    buyMonths[0] += effectiveTime;
                }
            }
        });

        return buyMonths[0];
    }


}
