package com.angelmusic.service;

import com.angelmusic.dao.model.ContentMission;
import com.angelmusic.utils.HttpCode;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
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
     * @param account       用户账号
     * @param contentId     内容编号
     * @param missionStatus 关卡状态
     * @return
     */
    @Before(Tx.class)
    public Ret updateContentMission(String account, int contentId, int missionStatus) {

        //先查询是否存在记录
        final ContentMission contentMission = ContentMission.ME.getContentMission(account, contentId);

        //存在则更新
        if (contentMission != null) {
            contentMission.set("game_mission", missionStatus).save();
        }

        ContentMission.ME.saveContentMission(account, contentId, missionStatus, missionStatus);
        return Ret.create("code", HttpCode.SUCCESS);
    }

}
