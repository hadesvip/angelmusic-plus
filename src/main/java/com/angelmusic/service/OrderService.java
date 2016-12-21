package com.angelmusic.service;

import com.angelmusic.dao.model.OrderRecord;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.List;
import java.util.UUID;

/**
 * 订单服务
 * Created by wangyong on 16-12-21.
 */
public class OrderService {

    public static final OrderService ORDERSERVICE = new OrderService();

    /**
     * 获取用户的订单信息
     *
     * @param userId 用户编号
     * @return
     */
    public List<OrderRecord> getUserOrderList(String userId) {
        return OrderRecord.ME.getUserOrderList(userId);
    }


    /**
     * 创建订单记录
     *
     * @param userId  用户编号
     * @param money   消费金额
     * @param product 产品
     * @param type    支付类型
     * @return
     */
    @Before(Tx.class)
    public HttpResult createOrderRecord(String userId, String money, String product, int type) {

        //订单号
        String orderId = UUID.randomUUID().toString();

        //成功订单成功
        if (OrderRecord.ME.saveOrderRecord(orderId, userId, money, product, type)) {
            return new HttpResult(HttpCode.SUCCESS, HttpCode.ORDER_RECORD_SAVE_SUCESS_WORD, OrderRecord.ME);
        }
        return new HttpResult(HttpCode.ORDER_RECORD_CREATE_FAIL, HttpCode.ORDER_RECORD_CREATE_FAIL_WORD);
    }

    /**
     * 更新订单支付结果
     *
     * @param userId    用户编号
     * @param orderId   订单编号
     * @param payResult 支付结果
     * @return
     */
    @Before(Tx.class)
    public HttpResult updateOrderRecord(String userId, String orderId, String payResult) {

        //更新成功
        if (OrderRecord.ME.updatePayResult(userId, orderId, payResult)) {
            return new HttpResult(HttpCode.SUCCESS, HttpCode.ORDER_UPDATE_SCUCESS_WORD);
        }

        return new HttpResult(HttpCode.ORDER_RECORD_CREATE_FAIL, HttpCode.ORDER_RECORD_CREATE_FAIL_WORD);
    }


}
