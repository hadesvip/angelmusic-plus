package com.angelmusic.service;

import com.angelmusic.dao.model.ActivationCode;
import com.angelmusic.dao.model.GiftPack;
import com.angelmusic.dao.model.OrderRecord;
import com.angelmusic.dao.model.Topic;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * 主题服务
 * Created by wangyong on 16-12-20.
 */
public class TopicService {

    public static final TopicService TOPICSERVICE = new TopicService();

    /**
     * 加载主题
     *
     * @param userId 用户编号
     * @return
     */
    public HttpResult loadTopicList(String userId) {

        //加载所有主题
        List<Topic> topics = Topic.ME.topics();

        //用户消费记录数
        //int orderCount = OrderRecord.ME.getOrderCountByUserId(userId);
        final List<OrderRecord> userOrderList = OrderService.ORDERSERVICE.getUserOrderList(userId);

        //计算出用户总共买了几个月
        final int[] buyMonths = {0};
        userOrderList.forEach(orderRecord -> {
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

        //用户总共买了多少个月
        //int buyMonths = userOrderList.size() * 12;
        final int[] count = {0};
        topics.stream().filter(topic -> topic.getInt("free") == Constant.TOPIC_UNFREE && buyMonths[0] > 0)
                .forEach(topic -> {
                    count[0]++;
                    //主题时间
                    Date topicDate = topic.getDate("topic_date");
                    DateTime dateTime = new DateTime(topicDate);
                    int topicMillis = dateTime.getYear() + dateTime.getMonthOfYear();
                    int nowMillis = DateTime.now().getYear() + DateTime.now().getMonthOfYear();

                    //是否在当前时间之前开区间
                    if (topicMillis <= nowMillis && buyMonths[0] >= count[0]) {
                        topic.setLock(Constant.TOPIC_UNLOCKED);
                    }
                });

        topics.stream().filter(topic -> topic.getInt("free") != Constant.TOPIC_UNFREE).forEach(topic -> topic.setLock(Constant.TOPIC_UNLOCKED));

 /*       for (Topic topic : topics) {
            int free = topic.getInt("free");
            //收费
            if (free == Constant.TOPIC_UNFREE && buyMonths > 0) {
                count[0]++;
                //主题时间
                Date topicDate = topic.getDate("topic_date");
                DateTime dateTime = new DateTime(topicDate);
                int topicMillis = dateTime.getYear() + dateTime.getMonthOfYear();
                int nowMillis = DateTime.now().getYear() + DateTime.now().getMonthOfYear();

                //是否在当前时间之前开区间
                if (topicMillis <= nowMillis && buyMonths >= count[0]) {
                    topic.setLock(Constant.TOPIC_UNLOCKED);
                }
            } else {
                // 未锁
                topic.setLock(Constant.TOPIC_UNLOCKED);
            }
        }*/

        return new HttpResult(HttpCode.SUCCESS, HttpCode.TOPICS_LOAD_SUCCESS_WORD, topics);
    }


}
