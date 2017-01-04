package com.angelmusic.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.angelmusic.dao.model.*;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.DateUtils;
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
        int userThemeCount = 0;

        //查询有哪些主题
        List<Topic> topics = Topic.ME.topics();
        if (CollectionUtils.isEmpty(topics)) {
            return ret;
        }

        Map<String,Object> returnMap = new HashMap<>();
        //处理结果
        List<Topic> tList = new ArrayList<>();
        //遍历当前主题
        for (int i = 0; i < topics.size(); i++) {
            //此处是试看主题的逻辑
            if (userThemeCount == 0) {
                //约定第一个为试看主题（呵呵）
                Topic freeTopic = topics.get(0);
                //取得这个主题的内容
                Integer topic_id = freeTopic.getInt("topic_id");

                List<Content> contents = Content.ME.getTopicContentList(freeTopic.getInt("topic_id"));
                //放入符合条件的content
                List<Content> cList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(contents)) {
                    //不为空取试看的
                    for (Content c : contents) {
                        if (2 == c.getInt("content_free")) {//试看的内容
                            cList.add(c);
                        } else {//不是试看跳出循环
                            break;
                        }
                    }
                    if (cList.size() > 0) {
                        freeTopic.setContentList(cList);
                    }
                    tList.add(freeTopic);
                }
            } else {
                //此处是对于已经购买过的用户的主题逻辑
                //判断当前主题+1是不是大于用户解锁主题  用于跳出循环
                if (i + 1 > userThemeCount) {
                    break;
                }

                //获取主题
                Topic t = topics.get(i);
                //获取该主题下的所有内容
                List<Content> contents = Content.ME.getTopicContentList(t.getInt("topic_id"));
                //获取用户游戏通关主题
                ContentMission mission = ContentMission.ME.getContentMissionByAccount(account);

                //放入符合条件的contentList
                List<Content> cList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(contents)) {//不为空  执行下面逻辑
                    for (int j = 0; j < contents.size(); j++) {
                        Content c = contents.get(j);
                        //判断内容下的游戏是不是通关
                        if (2 == c.getInt("content_free")) {//试看的内容 不用处理直接放
                            cList.add(c);
                        } else {
                            if (c.getInt("content_id") - 1 > mission.getInt("content_id")) {
                                break;
                            }
                            cList.add(c);
                        }
                    }
                }
                //主题存入最后符合条件的内容list
                if (cList.size() > 0) {
                    t.setContentList(cList);
                }

                //主题加入要返回的list
                tList.add(t);
            }
        }
        returnMap.put("topicList",tList);
        //查询大礼包
        List<GiftPack> giftPackList = GiftPack.ME.getAllGiftPack();
        returnMap.put("giftPackList",giftPackList);

        ret.put("detail", returnMap);
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
            /*if (free == Constant.FREE || free == Constant.PARTS_FREE) {
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
