package com.angelmusic.service;

import com.angelmusic.dao.model.Content;
import com.angelmusic.dao.model.ContentMission;
import com.angelmusic.utils.HttpCode;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
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
     * @param account   用户账号
     * @param contentId 内容编号
     * @return
     */
    @Before(Tx.class)
    public Ret updateContentMission(String account, int contentId) {

        //判断内容是否存在
        Content content = Content.ME.getContent(contentId);
        if (content == null) {
            return Ret.create("code", HttpCode.CONTENT_NOT_EXIST);
        }

        //先查询是否存在记录
        final ContentMission contentMission = ContentMission.ME.getContentMission(account);


        //存在则更新
        if (contentMission != null) {
            ContentMission.ME.updateContentMission(account, contentId);
        } else {
            ContentMission.ME.saveContentMission(account, contentId);
        }

        return Ret.create("code", HttpCode.SUCCESS).put("detail", Content.ME.getNextContent(contentId));
    }

}
