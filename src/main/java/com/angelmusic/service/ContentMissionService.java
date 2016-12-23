package com.angelmusic.service;

import com.angelmusic.dao.model.ContentMission;
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
     * @param gameMission    游戏
     * @param courseMission  课程
     * @return
     */
    @Before(Tx.class)
    public HttpResult updateContentMission(String userPhone, int topicContentId, int gameMission, int courseMission) {

        //TODO 先查询是否存在记录

        boolean updateResult = ContentMission.ME.updateContentMission(userPhone, topicContentId, gameMission, courseMission);

        //更新成功
        if (updateResult) {
            return new HttpResult(HttpCode.SUCCESS, HttpCode.MISSION_UPDATE_SUCCESS_WORD);
        }

        return new HttpResult(HttpCode.FAIL, HttpCode.MISSION_UPDATE_FAIL_WORD);
    }
}
