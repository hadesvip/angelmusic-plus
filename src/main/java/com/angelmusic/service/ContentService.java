package com.angelmusic.service;

import com.angelmusic.dao.model.Content;
import com.angelmusic.dao.model.Topic;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 内容服务
 * Created by wangyong on 16-12-22.
 */
public class ContentService {

    public final static ContentService ME = new ContentService();


    /**
     * 根据主题编号，用户编号获取当前用户的内容
     *
     * @param topicId 主题编号
     * @param userId  用户编号
     * @return
     */
    public HttpResult getTopicContentList(String topicId, String userId) {

        //获取主题
        final Topic topic = Topic.ME.getTopic(Integer.parseInt(topicId.trim()));

        //算出用户购买了几个月
        int buyMonths = OrderService.ORDERSERVICE.userMonths(userId);

        final int count[] = {0};

        //获取主题下面的内容
        List<Content> contentList = Content.ME.getTopicContentList(topicId);
        contentList.forEach(content -> {
            //是否免费
            int free = content.getInt("free");

            //免费
            if (free == Constant.TOPIC_FREE) {
                content.setLock(Constant.TOPIC_UNLOCKED);
            } else {

                //收费月份
                count[0]++;

                //主题时间
                DateTime topicDate = new DateTime(topic.getDate("topic_date"));
                DateTime currentDate = DateTime.now();
                long topicMills = topicDate.getYear() + topicDate.getMonthOfYear();
                long currMills = currentDate.getYear() + currentDate.getMonthOfYear();

                //买的月数大于当前收费月份并且主题时间小于当前时间
                if (buyMonths >= count[0] && topicMills <= currMills) {
                    content.setLock(Constant.TOPIC_UNLOCKED);
                }
            }
        });
        return new HttpResult(HttpCode.SUCCESS, null, contentList);
    }
}
