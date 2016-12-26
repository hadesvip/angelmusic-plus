package com.angelmusic.service;

import com.angelmusic.dao.model.ContentMission;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 内容关卡服务
 * Created by wangyong on 16-12-23.
 */
public class ContentMissionService {

    public static final ContentMissionService ME = new ContentMissionService();


    /**
     * 更新关卡状态
     *
     * @param userPhone      手机号
     * @param topicContentId 主题关系编号
     * @param type           类型
     * @param missionStatus  任务状态
     * @return
     */
    @Before(Tx.class)
    public HttpResult updateContentMission(String userPhone, int topicContentId, int type, int missionStatus) {

        //先查询是否存在记录
        final ContentMission contentMission = ContentMission.ME.getContentMission(userPhone, topicContentId);

        //存在则更新课程
        if (contentMission != null && Constant.MISSION_COURSE == type) {
            contentMission.set("course_mission", missionStatus).save();
        }

        //存在则更新游戏
        if (contentMission != null && Constant.MISSION_GAME == type) {
            contentMission.set("game_mission", missionStatus).save();
        }

        //课程
        if (Constant.MISSION_COURSE == type) {
            ContentMission.ME.saveContentMission(userPhone, topicContentId, Constant.MISSION_UNCOMPLETE, missionStatus);
        }

        //游戏
        if (Constant.MISSION_GAME == type) {
            ContentMission.ME.saveContentMission(userPhone, topicContentId, missionStatus, Constant.MISSION_UNCOMPLETE);
        }

        return new HttpResult(HttpCode.SUCCESS, HttpCode.MISSION_UPDATE_SUCCESS_WORD);
    }
}
