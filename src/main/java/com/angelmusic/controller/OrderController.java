package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.OrderService;
import com.angelmusic.service.RechargeRecordService;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.Ret;
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

        //只支持POST请求
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("get")) {
            renderJson(Ret.create("code", HttpCode.HTTP_ONLY_SUPPORT_POST).getData());
            return;
        }

        String account = getPara("account");
        String money = getPara("money");
        // 大礼包编号
        String product = getPara("product");

        //参数校验
        if (StrKit.isBlank(account) || StrKit.isBlank(money) || StrKit.isBlank(product)) {
            Ret.create("code", HttpCode.PARAMS_INVAILD);
            return;
        }

        //金额
        if (Integer.parseInt(money) <= 0) {
            renderJson(Ret.create("code", HttpCode.ORDER_RECORD_MONEY_LESS_ZERO).getData());
            return;
        }

        //创建订单
        renderJson(OrderService.ME.createOrderRecord(account, money, product).getData());

        LOGGER.info("[leave createOrderRecord]");
    }


    /**
     * 更新订单,并返回用户解锁主题
     */
    public void updateOrderPayResult() {
        LOGGER.info("[invoke updateOrderPayResult]");

        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("get")) {
            renderJson(Ret.create("code", HttpCode.HTTP_ONLY_SUPPORT_POST).getData());
            return;
        }

        String orderId = getPara("orderId");
        String payResult = getPara("payResult");
        String account = getPara("account");

        //参数异常
        if (StrKit.isBlank(orderId) || StrKit.isBlank(payResult) || StrKit.isBlank(account)) {
            renderJson(Ret.create("code", HttpCode.PARAMS_INVAILD).getData());
            return;
        }

        //更新订单
        renderJson(OrderService.ME.updateOrderRecord(orderId, payResult, account).getData());

        LOGGER.info("[leave updateOrderPayResult]");
    }


    /**
     * 同步统接sdk充值流水
     */
    public void synTJsdkRechargeRecord() {
        LOGGER.info("invoke synTJsdkRechargeRecord");

        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase("post")) {

            //订单号，订单状态，订单状态描述，订单价格(分)，透传参数，支付类型，加密校验参数
            String linkId = getPara("linkid");
            String chargeStatus = getPara("chargestatus");
            String chargeMsg = getPara("chargemsg");
            long price = getParaToLong("price");
            String cpParam = getPara("cpparam");
            String payType = getPara("paytype");
            String encryptData = getPara("encryptdata");

            //校验密码
            if (Constant.TJSDK_ENCRYPTDATA.equals(encryptData)) {
                //保存充值流水
                RechargeRecordService.ME.saveRechargeRecord(linkId, chargeStatus, chargeMsg, price, cpParam, payType);
            }

            LOGGER.info("[leave synTJsdkRechargeRecord]");
        }
    }


}
