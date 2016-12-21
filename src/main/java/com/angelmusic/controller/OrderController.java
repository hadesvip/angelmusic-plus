package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.OrderService;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单
 * Created by wangyong on 16-12-21.
 */
public class OrderController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(QrcodeController.class);

    /**
     * 创建订单
     */
    public void createOrderRecord() {
        LOGGER.info("[invoke createOrderRecord]");

        String userId = getPara("userId");
        String money = getPara("money");

        // 产品:二维码编号，或者大礼包
        String product = getPara("product");
        //支付类型
        int type = getParaToInt("type");

        //参数校验
        if (StrKit.isBlank(userId) || StrKit.isBlank(money) || StrKit.isBlank(product) || type <= 0) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.CREATEORDERRECORD_PARAMS_INVAILD);
            return;
        }

        //金额
        if (Integer.parseInt(money) <= 0) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.ORDER_RECORD_MONEY_LESS_ZERO_WORD);
            return;
        }

        //支付类型
        if (type < Constant.ORDER_TYPE_ACTIVATECODE || type > Constant.ORDER_TYPE_GIFT_PACK) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.ORDER_TYPE_OVER_WORD);
            return;
        }

        //创建订单
        renderJson(OrderService.ORDERSERVICE.createOrderRecord(userId, money, product, type));

        LOGGER.info("[leave createOrderRecord]");
    }


    /**
     * 更新订单支付结果
     */
    public void updateOrderPayResult() {
        LOGGER.info("[invoke updateOrderPayResult]");

        String userId = getPara("userId");
        String orderId = getPara("orderId");
        String payResult = getPara("payResult");

        //更新订单
        renderJson(OrderService.ORDERSERVICE.updateOrderRecord(userId, orderId, payResult));

        LOGGER.info("[leave updateOrderPayResult]");
    }


}
