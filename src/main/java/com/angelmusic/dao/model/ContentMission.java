package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
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
     * @param userPhone      用户手机号
     * @param topicContentId 主题内容编号
     * @return 内容关卡
     */
    public ContentMission getContentMission(String userPhone, int topicContentId) {
        return ME.findFirst(PlusSqlKit.sql("contentMission.getContentMission"), userPhone, topicContentId);
    }

    /**
     * 更新关卡
     *
     * @param userPhone      用户手机号
     * @param topicContentId 主题内容
     * @param gameMission    游戏
     * @param courseMission  课程
     * @return
     */
    public boolean updateContentMission(String userPhone, int topicContentId, int gameMission, int courseMission) {
        return Db.update(PlusSqlKit.sql("contentMission.updateContentMission"), gameMission, courseMission, userPhone, topicContentId) > 0;
    }
}
