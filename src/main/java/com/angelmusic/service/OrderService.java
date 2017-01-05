package com.angelmusic.service;

import com.angelmusic.dao.model.*;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 订单服务
 * Created by wangyong on 16-12-21.
 */
public class OrderService {

    public static final OrderService ME = new OrderService();

    /**
     * 创建订单记录
     *
     * @param account 用户账号
     * @param money   消费金额
     * @param product 产品
     * @return
     */
    @Before(Tx.class)
    public Ret createOrderRecord(String account, String money, String product) {

        //订单号
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");

        //成功订单成功
        if (OrderRecord.ME.saveOrderRecord(orderId, account, money, product, Constant.ORDER_TYPE_GIFT_PACK, Constant.PAY_PAYING)) {

            return Ret.create("code", HttpCode.SUCCESS).put("detail", OrderRecord.ME);
        }


        return Ret.create("code", HttpCode.ORDER_RECORD_CREATE_FAIL);
    }

    /**
     * 更新订单支付结果
     *
     * @param orderId   订单编号
     * @param payResult 支付结果
     * @param account   账号
     * @return
     */
    public Ret updateOrderRecord(String orderId, String payResult, String account) {

        Topic topic = null;
        //获取订单
        final OrderRecord order = OrderRecord.ME.getOrderByOrderId(orderId);

        if (order == null) {
            return Ret.create("code", HttpCode.ORDER_NOT_EXIST);
        }
        //支付成功
        if (Integer.parseInt(payResult) == Constant.PAY_SUCESS) {
            int type = order.getInt("type");
            String orderProduct = order.getStr("product");

            //更新权益结束时间
            calcOrderStartEndTime(account, type, orderProduct, orderId, Integer.parseInt(payResult));

            //用户目前解锁的主题数目
            topic = calcUserTopicNum(account);
        } else {
            OrderRecord.ME.updatePayResult(orderId, Integer.parseInt(payResult), null, null);
        }

        return Ret.create("code", HttpCode.SUCCESS).put("detail", topic);
    }

    /**
     * 计算出用户主题数
     *
     * @return
     */
    @Before(Tx.class)
    public static Topic calcUserTopicNum(String account) {
        UserTopic userTopic = UserTopic.ME.getUserTopic(account);
        if (userTopic != null) {
            int topicCount = userTopic.getInt("topic_count");
            final int[] count = {topicCount};
            final int[] nums = {0};

            //更新主题数
            OrderRecord.ME.getUserOrderList(account).forEach(orderRecord -> {

                //到期时间
                final DateTime orderEndTime = new DateTime(orderRecord.getDate("end_time"));
                final DateTime orderStartTime = new DateTime(orderRecord.getDate("start_time"));
                final int orderType = orderRecord.getInt("type");
                final String product = orderRecord.getStr("product");

                //权益在今天之前的进行计算,表示已经开始进行权益了
                DateTime now = DateTime.now();
                int nowDays = now.getYear() + now.getMonthOfYear() + now.getDayOfMonth();
                int orderDays = orderStartTime.getYear() + orderStartTime.getMonthOfYear() + orderStartTime.getDayOfMonth();
                int endDays = orderEndTime.getYear() + orderEndTime.getMonthOfYear() + orderEndTime.getDayOfMonth();

                //权益使用中，权益结束
                if (nowDays >= orderDays) {
                    nums[0] = 0;

                    //过期
                    if (endDays <= nowDays) {

                        //大礼包
                        if (orderType == Constant.ORDER_TYPE_GIFT_PACK) {
                            GiftPack giftPack = GiftPack.ME.getGiftPackById(product);
                            int months = giftPack.getInt("effective_time");
                            nums[0] = months;
                        }
                        //二维码
                        if (orderType == Constant.ORDER_TYPE_ACTIVATECODE) {
                            ActivationCode activationCode = ActivationCode.ME.getActivationCodeByCode(product);
                            int months = activationCode.getInt("effective_time");
                            nums[0] = months;
                        }
                    }
                    //未过期,计算出到目前为止能解锁的主题数
                    else {
                        int factor = now.getYear() > orderStartTime.getYear() ? (now.getYear() - orderStartTime.getYear()) * 12 : 0;
                        if (now.getDayOfMonth() >= orderStartTime.getDayOfMonth()) {
                            nums[0] = factor + now.getMonthOfYear() - orderStartTime.getMonthOfYear() + 1;
                        } else {
                            nums[0] = factor + now.getMonthOfYear() - orderStartTime.getMonthOfYear();
                        }
                    }
                    count[0] += nums[0];
                }

            });

            //更新主题数目
            UserTopic.ME.updateUserTopic(count[0], userTopic.getInt("id"));


            //解锁新的主题
            if (count[0] > topicCount) {
                Topic topic = Topic.ME.getTopicByOrder(count[0]);
                final List<Content> contentList = Content.ME.getTopicContentList(topic.getInt("topic_id"));
                topic.setContentList(contentList);
                return topic;
            }
        }
        //插入记录
        else {
            UserTopic.ME.saveUserTopic(account, 1);
            Topic topic = Topic.ME.getTopicByOrder(1);
            final List<Content> contentList = Content.ME.getTopicContentList(topic.getInt("topic_id"));
            topic.setContentList(contentList);
            return topic;
        }

        return null;
    }

    /**
     * 计算订单权益结束时间
     */
    @Before(Tx.class)
    public static void calcOrderStartEndTime(String account, int type, String orderProduct, String orderId, int payResult) {

        //取出用户离目前最近的订单
        final OrderRecord recentOrder = OrderRecord.ME.getRecentOrder(account);

        //权益开始时间
        DateTime startTime = DateTime.now();
        DateTime endTime;
        int months = 0;

        //大礼包
        if (type == Constant.ORDER_TYPE_GIFT_PACK) {
            GiftPack giftPack = GiftPack.ME.getGiftPackById(orderProduct);
            months = giftPack.getInt("effective_time");
        }
        //二维码
        if (type == Constant.ORDER_TYPE_ACTIVATECODE) {
            ActivationCode activationCode = ActivationCode.ME.getActivationCodeByCode(orderProduct);
            months = activationCode.getInt("effective_time");
        }

        //未过期，用户就订购
        if (recentOrder != null) {
            final Date rencentEndTime = recentOrder.getDate("end_time");
            //第二天开始计算
            startTime = new DateTime(recentOrder.getDate("end_time")).plusDays(1);
            endTime = new DateTime(rencentEndTime).plusMonths(months).plusDays(1);
        } else {
            endTime = startTime.plusMonths(months);
        }

        OrderRecord.ME.updatePayResult(orderId, payResult, startTime.toDate(), endTime.toDate());
    }

}
