package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 内容关卡
 * Created by wangyong on 16-12-23.
 */
@TableBind(pkName = "content_mission_id", tableName = "am_content_mission")
public class ContentMission extends Model<ContentMission> {

    public static final ContentMission ME = new ContentMission();


    /**
     * 内容关卡
     *
     * @param userPhone 用户手机号
     * @param contentId 内容编号
     * @return 内容关卡
     */
    public ContentMission getContentMission(String userPhone, int contentId) {
        return ME.findFirst(PlusSqlKit.sql("contentMission.getContentMission"), userPhone, contentId);
    }

    /**
     *
     * @param account
     * @return
     */
    public ContentMission getContentMissionByAccount(String account) {
        return ME.findFirst(PlusSqlKit.sql("contentMission.getContentMissionByAccount"), account);
    }


    /**
     * 获取上一个内容关卡
     *
     * @return
     */
    public ContentMission getPrevMission(String account, int topicId, int order) {
        return ME.findFirst("contentMission.getPrevContentMission", account, topicId, order);
    }


    /**
     * 保存内容关卡
     *
     * @param userPhone      手机号
     * @param topicContentId 主题内容编号
     * @param gameMission    游戏关卡
     * @param courseMission  课程关卡
     */
    public boolean saveContentMission(String userPhone, int topicContentId, int gameMission, int courseMission) {
        return
                ME
                        .set("user_phone", userPhone)
                        .set("content_id", topicContentId)
                        .set("game_mission", gameMission)
                        .save();
    }
}
