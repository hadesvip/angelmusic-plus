package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.angelmusic.utils.Constant;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.Date;
import java.util.List;

/**
 * 订单记录
 * Created by wangyong on 16-12-20.
 */
@TableBind(pkName = "order_id", tableName = "am_order_record")
public class OrderRecord extends Model<OrderRecord> {

    public static final OrderRecord ME = new OrderRecord();

    /**
     * 根据用户编号获取订单记录数
     *
     * @param userPhone 用户手机号
     * @return
     */
    public int getOrderCountByUserId(String userPhone) {
        return Db.queryInt(PlusSqlKit.sql("order.getOrderCountByUserId"), userPhone);
    }

    /**
     * 获取用户订单记录
     *
     * @param userPhone 用户手机号
     * @return
     */
    public List<OrderRecord> getUserOrderList(String userPhone) {
        return ME.find(PlusSqlKit.sql("order.getOrderList"), userPhone);
    }


    /**
     * 保存订单
     *
     * @param orderId   订单编号
     * @param userPhone 用户手机号
     * @param money     消费金额
     * @param product   产品
     * @param type      订单类型
     */
    public boolean saveOrderRecord(String orderId, String userPhone, String money, String product, int type) {
        return
                ME
                        .set("order_id", orderId)
                        .set("user_phone", userPhone)
                        .set("money", money)
                        .set("product", product)
                        .set("order_date", new Date())
                        .set("type", type)
                        .set("pay_result", Constant.PAY_PAYING)
                        .save();
    }

    /**
     * 根据订单编号获取订单记录
     *
     * @param orderId 订单编号
     * @return
     */
    public OrderRecord getOrderByOrderId(String orderId) {
        return ME.findById(orderId);
    }

    /**
     * @param orderId   订单编号
     * @param payResult 支付结果
     */
    public boolean updatePayResult(String orderId, String payResult) {
        return Db.update(PlusSqlKit.sql("order.updateOrderRecord"), payResult, orderId) > 0;
    }
}
