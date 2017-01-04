package com.angelmusic.service;

import com.angelmusic.dao.model.Content;
import com.angelmusic.dao.model.ContentMission;
import com.angelmusic.dao.model.GiftPack;
import com.angelmusic.dao.model.Topic;
import com.angelmusic.utils.HttpCode;
import com.jfinal.kit.Ret;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                List<Content> contents = Content.ME.getTopicContentList(topic_id);
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
}
