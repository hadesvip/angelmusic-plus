package com.angelmusic.controller;

import com.alibaba.fastjson.JSON;
import com.angelmusic.base.BaseController;
import com.angelmusic.service.OrderService;
import com.angelmusic.service.RechargeRecordService;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单
 * Created by wangyong on 16-12-21.
 */
@ControllerBind(controllerKey = "webapi/order")
public class OrderController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(QrcodeController.class);

    /**
     * 创建订单
     */
    public void createOrderRecord() {
        LOGGER.info("[invoke createOrderRecord]");
       /* String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("get")) {
            error("此接口只支持post请求");
            return;
        }*/

        String userPhone = getPara("userPhone");
        String money = getPara("money");

        // 产品:二维码编号，或者大礼包
        String product = getPara("product");
        //支付类型
        int type = getParaToInt("type");

        //参数校验
        if (StrKit.isBlank(userPhone) || StrKit.isBlank(money) || StrKit.isBlank(product) || type <= 0) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.CREATEORDERRECORD_PARAMS_INVAILD);
            return;
        }

        //金额
        if (Integer.parseInt(money) <= 0) {
            error(HttpCode.ORDER_RECORD_MONEY_LESS_ZERO, HttpCode.ORDER_RECORD_MONEY_LESS_ZERO_WORD);
            return;
        }

        //支付类型
        if (type < Constant.ORDER_TYPE_ACTIVATECODE || type > Constant.ORDER_TYPE_GIFT_PACK) {
            error(HttpCode.ORDER_TYPE_OVER, HttpCode.ORDER_TYPE_OVER_WORD);
            return;
        }

        //创建订单
        renderJson(OrderService.ORDERSERVICE.createOrderRecord(userPhone, money, product, type));

        LOGGER.info("[leave createOrderRecord]");
    }


    /**
     * 更新订单支付结果
     */
    public void updateOrderPayResult() {
        LOGGER.info("[invoke updateOrderPayResult]");

        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("get")) {
            error("此接口只支持post请求");
            return;
        }

        //String userId = getPara("userId");
        String orderId = getPara("orderId");
        String payResult = getPara("payResult");

        //参数异常
        if (StrKit.isBlank(orderId) || StrKit.isBlank(payResult)) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.PARAM_ORDERID_PAYRESULT_EMPTY_WORD);
            return;
        }

        //更新订单
        renderJson(OrderService.ORDERSERVICE.updateOrderRecord(orderId, payResult));

        LOGGER.info("[leave updateOrderPayResult]");
    }


    /**
     * 同步统接sdk充值流水
     */
    public void synTJsdkRechargeRecord() {
        LOGGER.info("invoke synTJsdkRechargeRecord");

        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("get")) {
            error("此接口只支持post请求");
            return;
        }

        //订单号，订单状态，订单状态描述，订单价格(分)，透传参数，支付类型，加密校验参数
        String linkId = getPara("linkid");
        String chargeStatus = getPara("chargestatus");
        String chargeMsg = getPara("chargemsg");
        long price = getParaToLong("price");
        String cpParam = getPara("cpparam");
        String payType = getPara("paytype");
        String encryptData = getPara("encryptdata");

        //校验密码
        if (!Constant.TJSDK_ENCRYPTDATA.equals(encryptData)) {
            error(HttpCode.TJ_ENCRYPTDATA_ERROR, HttpCode.TJ_ENCRYPTDATA_ERROR_WORD);
            return;
        }

        //保存充值流水
        RechargeRecordService.RECHARGERECORDSERVICE.saveRechargeRecord(linkId, chargeStatus, chargeMsg, price, cpParam, payType);

        LOGGER.info("[leave synTJsdkRechargeRecord]");
    }


}
