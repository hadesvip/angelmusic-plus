package com.angelmusic.service;

import com.angelmusic.dao.model.*;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.joda.time.DateTime;

import java.util.ArrayList;
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
     * @param type    支付类型
     * @return
     */
    @Before(Tx.class)
    public Ret createOrderRecord(String account, String money, String product, int type) {

        //订单号
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");

        //成功订单成功
        if (OrderRecord.ME.saveOrderRecord(orderId, account, money, product, type)) {

            return Ret.create("code", HttpCode.SUCCESS).put("detail", OrderRecord.ME);
        }

        return Ret.create("code", HttpCode.ORDER_RECORD_CREATE_FAIL).put("detail", OrderRecord.ME);
    }

    /**
     * 更新订单支付结果
     *
     * @param orderId   订单编号
     * @param payResult 支付结果
     * @param account   账号
     * @return
     */
    @Before(Tx.class)
    public Ret updateOrderRecord(String orderId, String payResult, String account) {

        //更新成功
        if (OrderRecord.ME.updatePayResult(orderId, payResult)) {

            List<Topic> topicList = new ArrayList<>();
            final List<Content>[] contentList = new ArrayList[]{null};

            //取出用户解锁主题
            Topic.ME.getTopicList().forEach(topic -> {
                contentList[0] = new ArrayList<>();

                //试用
                if (topic.getInt("free") == Constant.PARTS_FREE) {
                    topicList.add(topic);
                } else {

                    final int[] nums = new int[]{0};
                    //订单记录
                    OrderRecord.ME.getUserOrderList(account).forEach(orderRecord -> {
                        DateTime recordDate = new DateTime(orderRecord.getDate("order_date"));
                        String product = orderRecord.getStr("product");
                        int type = orderRecord.getInt("type");

                        //大礼包
                        if (type == Constant.ORDER_TYPE_GIFT_PACK) {
                            GiftPack giftPack = GiftPack.ME.getGiftPackByName(product);

                            //时效
                            int months = giftPack.getInt("effective_time");
                            DateTime giftPackTime = recordDate.plusMonths(months);
                            



                        }

                        //二维码
                        if (type == Constant.ORDER_TYPE_ACTIVATECODE) {
                            ActivationCode activationCode = ActivationCode.ME.getActivationCodeByCode(product);

                            //时效
                            int months = activationCode.getInt("effective_time");
                            DateTime activationCodeTime = recordDate.plusMonths(months);


                        }


                        DateTime now = DateTime.now();
                        int lockNum = 1;
                        int num = 0;
                        //因子
                        int factor = now.getYear() > recordDate.getYear() ? (now.getYear() - recordDate.getYear()) * 12 : 0;
                        if (now.getDayOfMonth() >= recordDate.getDayOfMonth()) {
                            num = factor + now.getMonthOfYear() - recordDate.getMonthOfYear() + lockNum;
                        } else {
                            num = factor + now.getMonthOfYear() - recordDate.getMonthOfYear();
                        }


                    });


                }

                //内部内容判断
                Content.ME.getTopicContentList(topic.getInt("topic_id")).forEach(content -> {
                    if (content.getInt("content_free") == Constant.CONTENT_FREE) {
                        contentList[0].add(content);
                    } else {
                        //用户上一个关卡是否通关，并且用户是否购买了该主题
                        final ContentMission contentMission = ContentMission.ME.getContentMission(account);
                        if (contentMission.getInt("content_id") + 1 >= content.getInt("content_id")) {

                        }

                    }
                });


                topicList.add(topic);

            });


            return Ret.create("code", HttpCode.SUCCESS);
        }


        return Ret.create("code", HttpCode.ORDER_UPDATE_FAIL);
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
//
//    public boolean checkUserTopic(String account) {
//        //获取所有主题
//        OrderRecord.ME.getUserOrderList(account).forEach(orderRecord -> {
//            DateTime recordDate = new DateTime(orderRecord.getDate("order_date"));
//            DateTime now = DateTime.now();
//            int defaultTopicNum = 1;
//            int num = 0;
//            //因子
//            int factor = now.getYear() > recordDate.getYear() ? (now.getYear() - recordDate.getYear()) * 12 : 0;
//            if (now.getDayOfMonth() >= recordDate.getDayOfMonth()) {
//                num = factor + now.getMonthOfYear() - recordDate.getMonthOfYear() + defaultTopicNum;
//            } else {
//                num = factor + now.getMonthOfYear() - recordDate.getMonthOfYear();
//            }
//
//
//        });
//
//        return false;
//    }

    public static void main(String[] args) {

        System.out.println(new Date(2016, 6, 3));

        DateTime recordDate = new DateTime(new Date(2016, 6, 5));
        DateTime now = DateTime.now();
        int defaultTopicNum = 1;
        int num = 0;
        //因子
        int factor = now.getYear() > recordDate.getYear() - 1900 ? (now.getYear() % (recordDate.getYear() - 1900)) * 12 : 0;
        System.out.println(factor);
        if (now.getDayOfMonth() >= recordDate.getDayOfMonth()) {
            num = factor + now.getMonthOfYear() - recordDate.getMonthOfYear() + defaultTopicNum;
            System.out.println(factor);
        } else {
            num = factor + now.getMonthOfYear() - recordDate.getMonthOfYear();
            System.out.println("-" + factor);
        }

        System.out.println(num);
    }


}
