package com.angelmusic.service;

import com.angelmusic.dao.model.*;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.ArrayList;
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

                //内部内容判断
                Content.ME.getTopicContentList(topic.getInt("topic_id")).forEach(content -> {
                    if (content.getInt("content_free") == Constant.CONTENT_FREE) {
                        contentList[0].add(content);
                    } else {
                        //用户上一个关卡是否通关，并且用户是否购买了该主题
                        ContentMission prevMission = ContentMission.ME.getPrevMission(account, topic.getInt("topic_id"), content.getInt("order"));
                        if (prevMission != null) {
                            int gameMission = prevMission.getInt("game_mission");
                            if (gameMission == Constant.MISSION_COMPLETE) {

                            }
                        }


                    }


                });

                topicList.add(topic);


                //试看
                if (topic.getInt("topic_free") == Constant.PARTS_FREE) {


                }

                //收费
                if (topic.getInt("free") == Constant.UNFREE) {

                }


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


}
