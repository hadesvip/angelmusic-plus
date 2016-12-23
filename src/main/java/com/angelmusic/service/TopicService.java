package com.angelmusic.service;

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

        //计算出用户总共买了几个月
        final int buyMonths = OrderService.ORDERSERVICE.userMonths(userId);

        //设置主题锁状态
        final int[] count = {0};
        topics.forEach(topic -> {
            int free = topic.getInt("free");

            //免费
            if (free == Constant.FREE || free == Constant.PARTS_FREE) {
                topic.setLock(Constant.UNLOCKED);
            } else {
                //收费主题
                count[0]++;

                //主题时间
                Date topicDate = topic.getDate("topic_date");
                DateTime dateTime = new DateTime(topicDate);
                int topicMillis = dateTime.getYear() + dateTime.getMonthOfYear();
                int nowMillis = DateTime.now().getYear() + DateTime.now().getMonthOfYear();

                //是否在当前时间之前开区间
                if (topicMillis <= nowMillis && count[0] <= buyMonths) {
                    topic.setLock(Constant.UNLOCKED);
                }
            }

        });

        return new HttpResult(HttpCode.SUCCESS, HttpCode.TOPICS_LOAD_SUCCESS_WORD, topics);
    }


}
