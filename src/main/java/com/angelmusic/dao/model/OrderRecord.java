package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
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
     * @param account 用户账号
     * @return
     */
    public List<OrderRecord> getUserOrderList(String account) {
        return ME.find(PlusSqlKit.sql("order.getOrderList"), account);
    }

    /**
     * @param account
     * @return
     */
    public OrderRecord getUserOrder(String account) {
        return ME.findFirst(PlusSqlKit.sql("order.getUserOrder"), account);
    }


    /**
     * 保存订单
     *
     * @param orderId 订单编号
     * @param account 用户账号
     * @param money   消费金额
     * @param product 产品
     * @param type    订单类型
     */
    public boolean saveOrderRecord(String orderId, String account, String money, String product, int type, int payResult) {
        return
                ME
                        .set("order_id", orderId)
                        .set("account", account)
                        .set("money", money)
                        .set("product", product)
                        .set("order_date", new Date())
                        .set("type", type)
                        .set("pay_result", payResult)
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
    public boolean updatePayResult(String orderId, String payResult, Date startTime, Date endTime) {
        return Db.update(PlusSqlKit.sql("order.updateOrderRecord"), payResult, startTime, endTime, orderId) > 0;
    }

    /**
     * 获取最近的订单
     *
     * @param account
     * @return
     */
    public OrderRecord getRecentOrder(String account) {
        return findFirst(PlusSqlKit.sql("order.getRecentOrder"), account);
    }
}
