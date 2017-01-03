package com.angelmusic.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.angelmusic.dao.model.Content;
import com.angelmusic.dao.model.Topic;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.json.FastJson;
import com.jfinal.json.Json;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import java.util.*;

/**
 * 主题服务
 * Created by wangyong on 16-12-20.
 */
public class TopicService {

    public static final TopicService ME = new TopicService();


    public Ret getData(String account) {
        Ret ret = Ret.create("code", HttpCode.SUCCESS).put("detail", "");

        //先查询用户解锁到第几个主题
        int userThemeCount = 6;

        //查询有哪些主题
        List<Topic> topics = Topic.ME.topics();
        if (CollectionUtils.isEmpty(topics)) {
            return ret;
        }

        //处理结果
        JSONArray ja = new JSONArray();
        //遍历当前主题
        for (int i = 0; i < topics.size(); i++) {
            //此处是试看主题的逻辑
            if (userThemeCount == 0) {
                Topic freeTopic = topics.get(0);
                JSONObject freeTjson = JSONObject.parseObject(JSON.toJSONString(freeTopic));
                //取得这个主题的内容
                List<Content> contents = new ArrayList<>();

                if (CollectionUtils.isNotEmpty(contents)) {
                    //不为空取试看的
                    for (Content c : contents) {
                        if (2 == c.getInt("free")) {//试看的内容
                            freeTjson.put("content", c);
                        } else {//不是试看跳出循环
                            break;
                        }
                    }
                }

                //放入返回json
                ja.add(freeTjson);
                ret.put("detail",ja);
                return ret;
            }

            //判断当前主题+1是不是等于大于用户解锁主题  用于跳出循环
            if (i + 1 >= userThemeCount) {
                break;
            }

            //获取主题
            Topic t = topics.get(i);
            FastJson json = FastJson.getJson();
            String s = json.toJson(t);
            JSONObject tJson = JSONObject.parseObject(s);
            //获取该主题下的所有内容
            List<Content> contents = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(contents)) {//不为空  执行下面逻辑

            }
        }


        return ret;
    }

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
        final int buyMonths = OrderService.ME.userMonths(userId);

        //设置主题锁状态
        final int[] count = {0};
        topics.forEach(topic -> {
            int free = topic.getInt("free");

            //免费
  /*          if (free == Constant.FREE || free == Constant.PARTS_FREE) {
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
            }*/

        });

        return new HttpResult(HttpCode.SUCCESS, HttpCode.TOPICS_LOAD_SUCCESS_WORD, topics);
    }


}
