package com.angelmusic.service;

import com.angelmusic.dao.model.Content;
import com.angelmusic.dao.model.ContentMission;
import com.angelmusic.dao.model.TopicContent;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;

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
     * @param topicId   主题编号
     * @param userPhone 用户手机号
     * @return
     */
    public HttpResult getTopicContentList(String topicId, String userPhone) {

        //获取主题下面的内容
        List<Content> contentList = Content.ME.getTopicContentList(topicId);
        contentList.forEach(content -> {
            int free = content.getInt("free");
            int topicContentId = content.getInt("topic_content_id");

            //免费
            if (free == Constant.TOPIC_FREE) {
                content.setLock(Constant.UNLOCKED);
            } else {

                //不是免费的用户通关后才能解锁
                ContentMission contentMission = ContentMission.ME.getContentMission(userPhone, topicContentId);
                int gameMission = contentMission.getInt("game_mission");
                int courseMission = contentMission.getInt("course_mission");

                //关卡完成，解锁
                if (gameMission == Constant.MISSION_COMPLETE && courseMission == Constant.MISSION_COMPLETE) {
                    content.setLock(Constant.UNLOCKED);
                }
            }
        });
        return new HttpResult(HttpCode.SUCCESS, null, contentList);
    }

    /**
     * 获取视频和游戏资源
     *
     * @param topicId   主题编号
     * @param contentId 内容编号
     * @return
     */
    public HttpResult getVedioAndGame(int topicId, int contentId) {
        return new HttpResult(HttpCode.SUCCESS, null, TopicContent.ME.getTopicContent(topicId, contentId));
    }
}
