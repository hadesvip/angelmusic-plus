package com.angelmusic.service;

import com.angelmusic.dao.model.Content;
import com.angelmusic.dao.model.ContentMission;
import com.angelmusic.dao.model.Topic;
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
        final List<Content> contentList = Content.ME.getTopicContentList(topicId);

        //算出用户购买了多少个月
        final int userMonths = OrderService.ME.userMonths(userPhone);

        //计算主题是收费主题中第几个月
        final List<Topic> topicList = Topic.ME.getTopicList();
        final int[] monthIndex = {0};
        for (int i = 0; i < topicList.size(); i++) {
            if (topicList.get(i).getInt("topic_id") == Integer.parseInt(topicId)) {
                monthIndex[0] = i + 1;
                break;
            }
        }

        //第一个永远解锁
        contentList.stream().findFirst().get().setLock(Constant.UNLOCKED);

        //解锁的条件当前任务所在的主题购买了并且上一个任务完成了
        contentList.forEach(content -> {
            int free = content.getInt("free");
            int contentId = content.getInt("content_id");

            //免费
            if (free == Constant.FREE) {
                content.setLock(Constant.UNLOCKED);
            }

            //排除第一个
            if (free != Constant.FREE && contentList.stream().findFirst().get().getInt("content_id") != contentId) {
                //查询上一个content
                Content prevContent = content.getPrevContent(Integer.parseInt(topicId), contentId);
                int prevTopContentId = prevContent.getInt("topic_content_id");

                //上一个关卡用户是否完成
                ContentMission contentMission = ContentMission.ME.getContentMission(userPhone, prevTopContentId);

                //任务完成并且用户购买了此关卡主题
                if (contentMission != null && contentMission.getInt("game_mission") == Constant.MISSION_COMPLETE
                        && contentMission.getInt("course_mission") == Constant.MISSION_COMPLETE && monthIndex[0] <= userMonths) {
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
        //return new HttpResult(HttpCode.SUCCESS, null, TopicContent.ME.getTopicContent(topicId, contentId));
        return null;
    }
}
